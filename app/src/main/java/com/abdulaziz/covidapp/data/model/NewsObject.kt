package com.abdulaziz.covidapp.data.model

import com.abdulaziz.covidapp.data.local.entitiy.NewsEntity

data class NewsObject(
    val `data`: List<NewsEntity>,
    val meta: Meta
)