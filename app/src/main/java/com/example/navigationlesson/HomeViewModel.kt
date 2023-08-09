package com.example.navigationlesson

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel:ViewModel() {


    sealed class HomeUiState{
        object Idle:HomeUiState()
        object Second:HomeUiState()
    }

    private val _homeUiState:MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Idle)
    val homeUiState:StateFlow<HomeUiState> = _homeUiState


    fun setUIState(){
        _homeUiState.value = HomeUiState.Second
    }



}