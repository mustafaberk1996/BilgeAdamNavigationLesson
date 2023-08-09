package com.example.navigationlesson.data.state

import com.example.navigationlesson.data.database.entity.User

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object UserNotFound : LoginState()
    class Success(val user: User) : LoginState()
    class Error(throwable: Throwable) : LoginState()
}