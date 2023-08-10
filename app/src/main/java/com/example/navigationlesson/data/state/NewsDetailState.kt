package com.example.navigationlesson.data.state

import com.example.navigationlesson.data.database.entity.News

sealed class NewsDetailState {
    object Idle: NewsDetailState()
    class Success(val news: News): NewsDetailState()
    class Error(throwable: Throwable? = null): NewsDetailState()
}
