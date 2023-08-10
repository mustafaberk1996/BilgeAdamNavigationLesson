package com.example.navigationlesson.data.state

import com.example.navigationlesson.data.database.entity.Author

sealed class AuthorListState {
    object Idle:AuthorListState()
    object Empty:AuthorListState()
    class Result(val authors:List<Author>):AuthorListState()
    class Error(val throwable: Throwable):AuthorListState()
}