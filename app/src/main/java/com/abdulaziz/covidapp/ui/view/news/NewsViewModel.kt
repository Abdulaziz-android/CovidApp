package com.abdulaziz.covidapp.ui.view.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulaziz.covidapp.data.local.entitiy.NewsEntity
import com.abdulaziz.covidapp.repositories.NewsRepository
import com.abdulaziz.covidapp.utils.NetworkHelper
import com.abdulaziz.covidapp.utils.NewsResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val networkHelper: NetworkHelper
): ViewModel() {

    private val newsStateFlow = MutableStateFlow<NewsResource>(NewsResource.Loading)

    init {
        initNews()
    }

    private fun initNews() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DATE, -3)
                val oldDay = calendar.time

                val flow = repository.getNews()
                flow.catch {
                    newsStateFlow.value = NewsResource.Error(it.message.toString())
                }.collect {
                    if (it.isSuccessful) {
                        val list = it.body()!!.data
                        val allNews = repository.getAllNews() as ArrayList<NewsEntity>
                        allNews.forEach { entity ->
                            val newsDate = simpleDateFormat.parse(entity.published_at)
                            if (newsDate?.before(oldDay) == true && !entity.isSaved){
                                repository.removeNews(entity)
                            }
                        }
                        if (allNews.size<20){
                            list.forEach { it1->
                                if (repository.getNewsByUUID(it1.uuid)==null)
                                repository.saveNews(it1)
                            }
                        }
                        newsStateFlow.value = NewsResource.Success(repository.getAllNews() ?: emptyList())
                    } else {
                        newsStateFlow.value = NewsResource.Error(it.message())
                    }
                }
            }else{
                val allNews = repository.getAllNews()
                if (allNews!=null && allNews.isNotEmpty()){
                    newsStateFlow.value = NewsResource.Success(allNews)
                }else{
                    newsStateFlow.value = NewsResource.Error("No internet connection!")
                }
            }
        }
    }

    fun getNewsStateFlow(): MutableStateFlow<NewsResource> {
        return newsStateFlow
    }

}