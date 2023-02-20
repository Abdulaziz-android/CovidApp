package com.abdulaziz.covidapp.utils

import com.abdulaziz.covidapp.data.local.entitiy.GlobalScoreEntity
import com.abdulaziz.covidapp.data.model.global.GlobalData

sealed class GlobalScoreResource {
    object Loading: GlobalScoreResource()
    data class Success(val globalData:GlobalData) : GlobalScoreResource()
    data class FetchLocal(val globalScoreEntity: GlobalScoreEntity) : GlobalScoreResource()
    data class Error(val message: String) : GlobalScoreResource()
}