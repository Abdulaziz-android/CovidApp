package com.abdulaziz.covidapp.utils.local_resurces

import android.content.Context
import com.abdulaziz.covidapp.R
import com.abdulaziz.covidapp.data.model.CustomDataModel

object PreventionData {

    fun getData(context: Context): ArrayList<CustomDataModel> {
        return arrayListOf(
            CustomDataModel(
                R.drawable.im_use_mask, context.getString(R.string.prevention_title_1),context.getString(
                    R.string.prevention_subtitle_1),context.getString(
                    R.string.prevention_description_1)),
            CustomDataModel(
                R.drawable.im_wash_your_hands,context.getString(R.string.prevention_title_2),context.getString(
                    R.string.prevention_subtitle_2),context.getString(
                    R.string.prevention_description_2)),
            CustomDataModel(
                R.drawable.im_avoid_animals_contact,context.getString(R.string.prevention_title_3),context.getString(
                    R.string.prevention_subtitle_3),context.getString(
                    R.string.prevention_description_3)),
            CustomDataModel(
                R.drawable.im_not_to_get_depressed,context.getString(R.string.prevention_title_4),context.getString(
                    R.string.prevention_subtitle_4),context.getString(
                    R.string.prevention_description_4)),
            CustomDataModel(
                R.drawable.im_regular_sports,context.getString(R.string.prevention_title_5),context.getString(
                    R.string.prevention_subtitle_5),context.getString(
                    R.string.prevention_description_5))
        )
    }

}
