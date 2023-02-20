package com.abdulaziz.covidapp.utils.local_resurces

import com.abdulaziz.covidapp.R
import com.abdulaziz.covidapp.data.model.CustomDataModel

object HelpData {

    fun getData(): ArrayList<CustomDataModel> {
        return arrayListOf(
            CustomDataModel(
                R.drawable.ic_phone, "Hotline","Call an ambulance"),
            CustomDataModel(
                R.drawable.ic_add_to_queue,"Doctor","Read doctor's recommendations"),
            CustomDataModel(
                R.drawable.ic_location_outline, "Hospital", "Track the location of hospitals near you"),
            CustomDataModel(
                R.drawable.ic_mail, "Consultation", "Contact a consultant via sms")
        )
    }

}
