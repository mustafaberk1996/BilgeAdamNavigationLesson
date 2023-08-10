package com.example.navigationlesson.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.navigationlesson.data.database.entity.Book

@Dao
interface BookDao {



    @Insert
    suspend fun insert(book: Book)


}