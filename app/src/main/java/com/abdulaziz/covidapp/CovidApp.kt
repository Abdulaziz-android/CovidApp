package com.abdulaziz.covidapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CovidApp : Application(){

    companion object{
        const val API_KEY_NEWS = "H1J12hztuqaZiKQgJMUvDyCfyIX1VvMuDfbYIdnE"
        const val SPINNER_POSITION = "spinner_position"
        const val CURRENT_COUNTRY = "current_country"
        const val DEFAULT_COUNTRY = "Uzbekistan"
        const val REGISTER = "user_is_registered"
    }

}