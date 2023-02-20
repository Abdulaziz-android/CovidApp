package com.abdulaziz.covidapp.data.model

import java.io.Serializable

data class CustomDataModel(
    val image_res_id: Int,
    val title: String,
    val subtitle: String,
    val content: String?=null
):Serializable