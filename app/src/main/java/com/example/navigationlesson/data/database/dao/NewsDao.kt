package com.example.navigationlesson.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.example.navigationlesson.data.database.entity.News
import com.example.navigationlesson.data.database.entity.User

@Dao
interface NewsDao {
    @Query("SELECT * FROM News")
    suspend fun getNews(): List<News>

    @Query("SELECT * FROM news WHERE id = :id")
    suspend fun getNewsById(id: Int) :News

    @Delete
    suspend fun deleteNews(news:News)
}