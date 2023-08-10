package com.example.navigationlesson.ui.newsDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationlesson.data.database.AppDatabase
import com.example.navigationlesson.data.state.NewsDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsDetailViewModel:ViewModel() {

    private val _newsDetailState: MutableStateFlow<NewsDetailState> = MutableStateFlow(NewsDetailState.Idle)
    val newsDetailState: StateFlow<NewsDetailState> = _newsDetailState

    fun getNewsById(database: AppDatabase, id:Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                _newsDetailState.emit(NewsDetailState.Success(database.newsDao().getNewsById(id)))
            }.onFailure {
                _newsDetailState.emit(NewsDetailState.Error(it))
            }
        }
    }
}