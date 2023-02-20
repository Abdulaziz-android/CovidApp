package com.abdulaziz.covidapp.utils.local_resurces

object CountriesData {

    fun getCountries():List<Country>{
        return arrayListOf(
            Country("uzb", "Uzbekistan"),
            Country("jpn", "Japan"),
            Country("rus", "Russia"),
            Country("ita", "Italy"),
            Country("esp", "Spain"),
            Country("arg", "Argentina"),
            Country("deu", "Germany"),
        )
    }

}

data class Country(
    val abbreviation:String,
    val name:String
)