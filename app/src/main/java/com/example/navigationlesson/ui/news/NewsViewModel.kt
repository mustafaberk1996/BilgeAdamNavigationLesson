package com.example.navigationlesson.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationlesson.data.database.AppDatabase
import com.example.navigationlesson.data.database.entity.News
import com.example.navigationlesson.data.state.AdapterState
import com.example.navigationlesson.data.state.NewsDetailState
import com.example.navigationlesson.data.state.NewsListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel:ViewModel() {


    private val _newsListState:MutableStateFlow<NewsListState> = MutableStateFlow(NewsListState.Idle)
    val newsListState:StateFlow<NewsListState> = _newsListState

    private val _adapterState:MutableStateFlow<AdapterState> = MutableStateFlow(AdapterState.Idle)
    val adapterState:StateFlow<AdapterState> = _adapterState

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
                    _newsListState.value = NewsListState.Success(newsList.toMutableList())
                }
            }.onFailure {
                _newsListState.value = NewsListState.Error(it)
            }

        }

    }

    fun remove(news: News, appDatabase:AppDatabase) {
        viewModelScope.launch {
            kotlin.runCatching {
                appDatabase.newsDao().deleteNews(news)
                if(_newsListState.value is NewsListState.Success) {
                    val index = (_newsListState.value as NewsListState.Success).news.indexOf(news)
                    (_newsListState.value as NewsListState.Success).news.remove(news)
                    _adapterState.value = AdapterState.Removed(index)
                }
            }.onFailure {

            }
        }
    }


}