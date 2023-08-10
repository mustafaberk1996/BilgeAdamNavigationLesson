package com.example.navigationlesson.data.state

import com.example.navigationlesson.data.database.entity.News

sealed class NewsListState {
    object Idle:NewsListState()
    object Loading:NewsListState()
    object Empty:NewsListState()
    class Success(val news:MutableList<News>):NewsListState()
    class Error(val throwable: Throwable):NewsListState()
}