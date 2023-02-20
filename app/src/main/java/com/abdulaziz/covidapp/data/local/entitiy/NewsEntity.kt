package com.abdulaziz.covidapp.data.local.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class NewsEntity(
    @PrimaryKey
    val uuid: String,
    val description: String,
    val image_url: String,
    val keywords: String,
    val language: String,
    val published_at: String,
    val snippet: String?=null,
    val source: String,
    val title: String,
    val url: String,
    var isSaved:Boolean= false,
    var viewCount:Int= 0
):Serializable