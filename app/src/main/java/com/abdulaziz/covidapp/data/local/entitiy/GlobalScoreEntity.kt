package com.abdulaziz.covidapp.data.local.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "global_score_table")
data class GlobalScoreEntity(
    val Date: String,
    val NewConfirmed: Int,
    val NewDeaths: Int,
    val NewRecovered: Int,
    val TotalConfirmed: Int,
    val TotalDeaths: Int,
    @PrimaryKey
    val TotalRecovered: Int
)