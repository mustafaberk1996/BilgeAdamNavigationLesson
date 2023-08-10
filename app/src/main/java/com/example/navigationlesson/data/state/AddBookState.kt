package com.example.navigationlesson.data.state

import com.example.navigationlesson.data.database.entity.Author

sealed class AddBookState {
    object Idle:AddBookState()
    object Success:AddBookState()
    object Error:AddBookState()
}