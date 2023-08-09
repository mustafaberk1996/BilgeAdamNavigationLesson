package com.example.navigationlesson.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.navigationlesson.data.database.entity.News
import com.example.navigationlesson.data.database.entity.User

@Dao
interface NewsDao {


    @Query("SELECT * FROM News")
    suspend fun getNews(): List<News>

}