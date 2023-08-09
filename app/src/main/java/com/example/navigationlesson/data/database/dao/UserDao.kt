package com.example.navigationlesson.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.navigationlesson.data.database.entity.User

@Dao
interface UserDao {


    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    suspend fun getUser(email:String, password:String):User?

}