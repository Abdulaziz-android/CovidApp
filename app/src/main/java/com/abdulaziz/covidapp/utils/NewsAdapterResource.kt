package com.abdulaziz.covidapp.utils

sealed class NewsAdapterResource {
    object Saved: NewsAdapterResource()
    object Recently: NewsAdapterResource()
    object Highlighted: NewsAdapterResource()
}