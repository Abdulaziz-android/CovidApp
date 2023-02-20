package com.abdulaziz.covidapp.utils.local_resurces

import android.content.Context
import com.abdulaziz.covidapp.R
import com.abdulaziz.covidapp.data.model.CustomDataModel

object WelcomeResource {

    fun getResource(context: Context): ArrayList<CustomDataModel> {
        return arrayListOf(
            CustomDataModel(R.drawable.im_wear_mask, context.getString(R.string.welcome_title_1),context.getString(R.string.welcome_subtitle_1)),
            CustomDataModel(R.drawable.im_wash_hands,context.getString(R.string.welcome_title_2),context.getString(R.string.welcome_subtitle_2)),
            CustomDataModel(R.drawable.im_medical_care,context.getString(R.string.welcome_title_3),context.getString(R.string.welcome_subtitle_3))
        )
    }


}
