package com.example.navigationlesson.ui.addBook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.navigationlesson.R
import com.example.navigationlesson.data.database.Database
import com.example.navigationlesson.data.state.AddBookState
import com.example.navigationlesson.data.state.AuthorListState
import com.example.navigationlesson.databinding.FragmentAddBookBinding
import com.example.navigationlesson.showAlert
import kotlinx.coroutines.launch

class AddBookFragment : Fragment(R.layout.fragment_add_book) {


    private lateinit var binding: FragmentAddBookBinding

    private val viewModel: AddBookViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddBookBinding.bind(view)


        observeAuthorListState()
        observeAddBookState()

        viewModel.getAuthors(Database.getDatabase(requireContext()))

        binding.btnAddBook.setOnClickListener {
            viewModel.addBook(
                Database.getDatabase(requireContext()),
                binding.etName.text.toString(),
                binding.etPublisherName.text.toString(),
                binding.etPageNumber.text.toString()
            )
        }


        binding.spAuthor.onItemSelectedListener = object :OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.authorSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }



    }

    private fun observeAuthorListState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.authorListState.collect {
                    when (it) {
                        is AuthorListState.Idle -> {}
                        is AuthorListState.Result -> {
                            binding.spAuthor.adapter = ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_list_item_1,
                                it.authors.map { "${it.name} ${it.surname}" })
                        }

                        else -> {

                        }
                    }
                }


            }

        }
    }

    private fun observeAddBookState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.addBookState.collect {
                    when (it) {
                        is AddBookState.Idle -> {}
                        is AddBookState.Success -> {requireContext().showAlert("Basarili","Kitap Kaydi Basarili")
                            binding.etName.text.clear()
                            binding.etPublisherName.text.clear()
                            binding.etPageNumber.text.clear()

                            binding.spAuthor.setSelection(0)
                        }
                        is AddBookState.Error -> requireContext().showAlert("Uyari","Bir sorun olustu")
                    }
                }

            }

        }
    }
}