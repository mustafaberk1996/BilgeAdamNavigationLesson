package com.example.navigationlesson.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val title:String?,
    val content:String?,
    val imageUrl:String?,
)