package com.example.navigationlesson.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.navigationlesson.data.database.dao.NewsDao
import com.example.navigationlesson.data.database.dao.UserDao
import com.example.navigationlesson.data.database.entity.News
import com.example.navigationlesson.data.database.entity.User

@Database(
    entities = [User::class, News::class],
    version = 1
)
abstract class AppDatabase:RoomDatabase() {

    abstract fun userDao():UserDao
    abstract fun newsDao(): NewsDao
}