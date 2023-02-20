package com.abdulaziz.covidapp.ui.view.statistics.pages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulaziz.covidapp.data.local.entitiy.GlobalStatisticListEntity
import com.abdulaziz.covidapp.repositories.GlobalStatisticRepository
import com.abdulaziz.covidapp.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class GlobalViewModel @Inject constructor(
    private val globalRepository:GlobalStatisticRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val newsStateFlow = MutableStateFlow<NewsResource>(NewsResource.Loading)
    private val globalStateFlow = MutableStateFlow<GlobalScoreResource>(GlobalScoreResource.Loading)
    private val dailyStateFlow = MutableStateFlow<GlobalStatisticResource>(GlobalStatisticResource.Loading)

    init {
        fetchGlobalScore()
        fetchNews()
        fetchDailyStatistics()
    }

    private fun fetchDailyStatistics() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

                val calendar = Calendar.getInstance()
                val to = simpleDateFormat.format(calendar.time)
                calendar.add(Calendar.DATE, -5)
                val from = simpleDateFormat.format(calendar.time)
                val flow = globalRepository.getRemoteGlobalStatistics(from, to)

                flow.catch {
                    dailyStateFlow.value = GlobalStatisticResource.Error(it.message.toString())
                }.collect {
                    if (it.isSuccessful) {
                        val list = it.body()
                        if (list!=null && list.isNotEmpty()) {
                            globalRepository.saveGlobalStatistics(GlobalStatisticListEntity(list = list))
                            dailyStateFlow.value = GlobalStatisticResource.Success(list)
                        }
                    } else {
                        dailyStateFlow.value = GlobalStatisticResource.Error(it.message())
                    }
                }
            } else {
                val localStatistics = globalRepository.getLocalGlobalStatistics()
                if (localStatistics != null && localStatistics.list.isNotEmpty()) {
                    dailyStateFlow.value = GlobalStatisticResource.Success(localStatistics.list)
                } else {
                    dailyStateFlow.value = GlobalStatisticResource.Error("Internet not connected!")
                }
            }
        }
    }

    private fun fetchGlobalScore() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                val flow = globalRepository.getRemoteGlobalScore()
                flow.catch {
                    globalStateFlow.value = GlobalScoreResource.Error(it.message.toString())
                }.collect {
                    if (it.isSuccessful) {
                        val globalData = it.body()
                        globalRepository.saveGlobalScore(globalData!!.Global)
                        globalStateFlow.value = GlobalScoreResource.Success(globalData)
                    } else {
                        globalStateFlow.value = GlobalScoreResource.Error(it.message())
                    }
                }
            } else {
                val globalScore = globalRepository.getLocalGlobalScore()
                if (globalScore != null) {
                    globalStateFlow.value = GlobalScoreResource.FetchLocal(globalScore)
                } else {
                    globalStateFlow.value = GlobalScoreResource.Error("Internet not connected!")
                }
            }

        }
    }



    private fun fetchNews() {
        viewModelScope.launch {
            val allNews = globalRepository.getAllNews()
            if (allNews != null && allNews.isNotEmpty()) {
                val shuffled = allNews.shuffled()
                newsStateFlow.value = NewsResource.Success(shuffled ?: emptyList())
            }else {
                if (networkHelper.isNetworkConnected()) {
                    val flow = globalRepository.getNews()
                    flow.catch {
                        newsStateFlow.value = NewsResource.Error(it.message.toString())
                    }.collect {
                        if (it.isSuccessful) {
                            val list = it.body()!!.data
                            globalRepository.saveNewsList(list)
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

    fun getStatisticStateFlow(): MutableStateFlow<GlobalScoreResource> {
        return globalStateFlow
    }

    fun getDailyStateFlow(): MutableStateFlow<GlobalStatisticResource> {
        return dailyStateFlow
    }


}