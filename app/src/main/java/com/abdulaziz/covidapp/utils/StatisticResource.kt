package com.abdulaziz.covidapp.utils

import com.abdulaziz.covidapp.data.local.entitiy.CountryStatisticEntity

sealed class StatisticResource {
    object Loading: StatisticResource()
    data class Success(val statistic: CountryStatisticEntity) : StatisticResource()
    data class Error(val message: String) : StatisticResource()
}