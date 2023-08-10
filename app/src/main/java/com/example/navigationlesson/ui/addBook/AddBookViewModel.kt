package com.example.navigationlesson.ui.addBook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigationlesson.data.database.AppDatabase
import com.example.navigationlesson.data.database.entity.Author
import com.example.navigationlesson.data.database.entity.Book
import com.example.navigationlesson.data.state.AddBookState
import com.example.navigationlesson.data.state.AuthorListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddBookViewModel:ViewModel() {



    private val selectedAuthor:MutableStateFlow<Author?> = MutableStateFlow(null)

    private val _authorListState:MutableStateFlow<AuthorListState> = MutableStateFlow(AuthorListState.Idle)
    val authorListState: StateFlow<AuthorListState> = _authorListState

    private val _addBookState:MutableStateFlow<AddBookState> = MutableStateFlow(AddBookState.Idle)
    val addBookState: StateFlow<AddBookState> = _addBookState



    fun getAuthors(appDatabase: AppDatabase){
        viewModelScope.launch {
            kotlin.runCatching {
                val authors=  appDatabase.authorDao().getAll()
                _authorListState.value = if (authors.isEmpty()) AuthorListState.Empty else AuthorListState.Result(authors)
            }.onFailure {
                _authorListState.value = AuthorListState.Error(it)
            }
        }
    }


    fun addBook(database: AppDatabase, name: String, publisherName: String, pageNumber: String) {
        viewModelScope.launch {
            if (name.isEmpty() && publisherName.isEmpty() && pageNumber.isEmpty()) return@launch

            selectedAuthor.value?.let {
                val book = Book(
                    name = name,
                    authorId = it.id,
                    pageNumber = pageNumber.toInt(),
                    publisherName = publisherName
                )
                database.bookDao().insert(book)
                _addBookState.value = AddBookState.Success
            }?: kotlin.run {
                _addBookState.value = AddBookState.Error
            }

        }
    }

    fun authorSelected(position: Int) {
        viewModelScope.launch {
            if (_authorListState.value is AuthorListState.Result){
                val author = (_authorListState.value as AuthorListState.Result).authors[position]
                selectedAuthor.value = author
            }
        }

    }


}