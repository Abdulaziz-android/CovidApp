package com.abdulaziz.covidapp.ui.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdulaziz.covidapp.repositories.HomeRepository
import com.abdulaziz.covidapp.utils.NetworkHelper
import com.abdulaziz.covidapp.utils.NewsResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val networkHelper: NetworkHelper
): ViewModel() {

    private val newsStateFlow = MutableStateFlow<NewsResource>(NewsResource.Loading)

    init {
        initNews()
    }

    private fun initNews() {
        viewModelScope.launch {
            val allNews = repository.getAllNews()
            if (allNews != null && allNews.isNotEmpty()) {
                val shuffled = allNews.shuffled()
                newsStateFlow.value = NewsResource.Success(shuffled ?: emptyList())
            }else {
                if (networkHelper.isNetworkConnected()) {
                    val flow = repository.getNews()
                    flow.catch {
                        newsStateFlow.value = NewsResource.Error(it.message.toString())
                    }.collect {
                        if (it.isSuccessful) {
                            val list = it.body()!!.data
                            repository.saveNewsList(list)
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
}