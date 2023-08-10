package com.example.navigationlesson.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.navigationlesson.data.database.entity.Author

@Dao
interface AuthorDao {


    @Insert
    suspend fun insert(author: Author)

    @Query("SELECT * FROM Author")
    suspend fun getAll():List<Author>
}