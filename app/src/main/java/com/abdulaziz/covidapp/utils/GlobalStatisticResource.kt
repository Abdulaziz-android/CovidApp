package com.abdulaziz.covidapp.utils

import com.abdulaziz.covidapp.data.model.global.daily.GlobalStatistic

sealed class GlobalStatisticResource {
    object Loading: GlobalStatisticResource()
    data class Success(val list:List<GlobalStatistic>) : GlobalStatisticResource()
    data class Error(val message: String) : GlobalStatisticResource()
}