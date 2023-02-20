package com.abdulaziz.covidapp.ui.view.statistics.pages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulaziz.covidapp.data.local.entitiy.CountryStatisticEntity
import com.abdulaziz.covidapp.repositories.StatisticCountryRepository
import com.abdulaziz.covidapp.utils.NetworkHelper
import com.abdulaziz.covidapp.utils.NewsResource
import com.abdulaziz.covidapp.utils.StatisticResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CountryStatisticViewModel @Inject constructor(
    private val countryRepository: StatisticCountryRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {


    private val newsStateFlow = MutableStateFlow<NewsResource>(NewsResource.Loading)
    private val statisticStateFlow = MutableStateFlow<StatisticResource>(StatisticResource.Loading)

    fun setCountry(country: String){
        fetchNews()
        fetchStatistic(country)
    }

    private fun fetchStatistic(country: String) {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {

                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

                val calendar = Calendar.getInstance()
                val to = simpleDateFormat.format(calendar.time)
                calendar.add(Calendar.DATE, -5)
                val from = simpleDateFormat.format(calendar.time)
                val flow = countryRepository.getRemoteStatistics(country = country,from = from, to = to)

                flow.catch {
                    statisticStateFlow.value = StatisticResource.Error(it.message.toString())
                }.collect {
                    if (it.isSuccessful) {
                        val list = it.body()
                        val st = CountryStatisticEntity(
                            country = country,
                            date = list?.last()?.Date,
                            list = list ?: arrayListOf()
                        )
                        countryRepository.saveStatistics(st)
                        statisticStateFlow.value = StatisticResource.Success(st)
                    } else {
                        statisticStateFlow.value = StatisticResource.Error(it.message())
                    }
                }
            } else {
                val localStatistics = countryRepository.getLocalStatistics(country)
                if (localStatistics != null && localStatistics.list.isNotEmpty()) {
                    statisticStateFlow.value = StatisticResource.Success(localStatistics)
                } else {
                    statisticStateFlow.value = StatisticResource.Error("Internet not connected!")
                }
            }

        }
    }


    private fun fetchNews() {
        viewModelScope.launch {
            val allNews = countryRepository.getAllNews()
            if (allNews != null && allNews.isNotEmpty()) {
                val shuffled = allNews.shuffled()
                newsStateFlow.value = NewsResource.Success(shuffled ?: emptyList())
            }else {
                if (networkHelper.isNetworkConnected()) {
                    val flow = countryRepository.getNews()
                    flow.catch {
                        newsStateFlow.value = NewsResource.Error(it.message.toString())
                    }.collect {
                        if (it.isSuccessful) {
                            val list = it.body()!!.data
                            countryRepository.saveNewsList(list)
                            newsStateFlow.value = NewsResource.Success(list ?: emptyList())
                        } else {
                            newsStateFlow.value = NewsResource.Error(it.message())
                        }
                    }
                } else {
                    newsStateFlow.value = NewsResource.Error("No internet connection!")
                }
            }
        }

    }


    fun getNewsStateFlow(): MutableStateFlow<NewsResource> {
        return newsStateFlow
    }

    fun getStatisticStateFlow(): MutableStateFlow<StatisticResource> {
        return statisticStateFlow
    }


}