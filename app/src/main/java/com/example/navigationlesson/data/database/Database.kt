package com.example.navigationlesson.data.database

import android.content.Context
import androidx.room.Room

object Database {


    fun getDatabase(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "database").build()

}