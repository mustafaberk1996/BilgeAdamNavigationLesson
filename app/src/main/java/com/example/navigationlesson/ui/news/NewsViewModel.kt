package com.example.navigationlesson.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationlesson.data.database.AppDatabase
import com.example.navigationlesson.data.state.NewsListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel:ViewModel() {


    private val _newsListState:MutableStateFlow<NewsListState> = MutableStateFlow(NewsListState.Idle)
    val newsListState:StateFlow<NewsListState> = _newsListState

    fun getNews(database: AppDatabase) {
        viewModelScope.launch {
            kotlin.runCatching {
                _newsListState.value = NewsListState.Loading
                val newsList = database.newsDao().getNews()
                if (newsList.isEmpty()){
                    //statei empty olarak guncelle
                    _newsListState.value = NewsListState.Empty

                }else{
                    //statei dolu olarak set et
                    _newsListState.value = NewsListState.Success(newsList)
                }
            }.onFailure {
                _newsListState.value = NewsListState.Error(it)
            }

        }

    }


}