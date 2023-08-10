package com.example.navigationlesson.data.state

import com.example.navigationlesson.data.database.entity.Author

sealed class AddAuthorState {
    object Idle:AddAuthorState()
    class Success(val author:Author):AddAuthorState()
    class Error(val throwable: Throwable):AddAuthorState()
}