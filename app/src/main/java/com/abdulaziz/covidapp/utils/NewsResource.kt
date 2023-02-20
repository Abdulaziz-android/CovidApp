package com.abdulaziz.covidapp.utils

import com.abdulaziz.covidapp.data.local.entitiy.NewsEntity

sealed class NewsResource {
    object Loading: NewsResource()
    data class Success(val list: List<NewsEntity>) : NewsResource()
    data class Error(val message: String) : NewsResource()
}