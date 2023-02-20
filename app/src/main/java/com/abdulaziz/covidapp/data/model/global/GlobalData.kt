package com.abdulaziz.covidapp.data.model.global

import com.abdulaziz.covidapp.data.local.entitiy.GlobalScoreEntity

data class GlobalData(
    val Countries: List<Country>,
    val Date: String,
    val Global: GlobalScoreEntity,
    val ID: String,
    val Message: String
)