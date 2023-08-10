package com.example.navigationlesson.ui.addAuthor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationlesson.data.database.AppDatabase
import com.example.navigationlesson.data.database.entity.Author
import com.example.navigationlesson.data.state.AddAuthorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddAuthorViewModel:ViewModel() {

    private val _addAuthorState:MutableStateFlow<AddAuthorState> = MutableStateFlow(AddAuthorState.Idle)
    val addAuthorState: StateFlow<AddAuthorState> = _addAuthorState

    fun saveAuthor(appDatabase: AppDatabase, name:String, surname:String){
        viewModelScope.launch {
            kotlin.runCatching {
                val author = Author(name = name, surname = surname)
                appDatabase.authorDao().insert(author)
                _addAuthorState.value = AddAuthorState.Success(author)
            }.onFailure {
                _addAuthorState.value = AddAuthorState.Error(it)
            }

        }


    }
}