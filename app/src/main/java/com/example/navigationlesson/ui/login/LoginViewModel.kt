package com.example.navigationlesson.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationlesson.data.database.AppDatabase
import com.example.navigationlesson.data.state.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LoginViewModel:ViewModel() {

    private val _loginState:MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Idle)
    val loginState:StateFlow<LoginState> = _loginState

    fun login(database: AppDatabase, email: String, password: String) {
        viewModelScope.launch {
            runCatching {
                _loginState.value = LoginState.Loading
                database.userDao().getUser(email, password)?.let {
                    //user var kullaniic giris yapabilir
                    _loginState.value = LoginState.Success(it)
                }?: kotlin.run {
                    //kullanici bulunamadi, email yada parola hatali
                    _loginState.value = LoginState.UserNotFound
                }
            }.onFailure {
                //hata var uyari goster
                _loginState.value = LoginState.Error(it)
            }
        }
    }


}