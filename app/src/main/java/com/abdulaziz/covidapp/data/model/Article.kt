package com.abdulaziz.covidapp.data.model

import java.io.Serializable

data class Article(
    val title: String,
    val description: String,
    val content: String,
    val image_url: String,
):Serializable
