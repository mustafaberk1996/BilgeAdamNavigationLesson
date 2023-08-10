package com.example.navigationlesson.ui.addAuthor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.navigationlesson.R
import com.example.navigationlesson.data.database.Database
import com.example.navigationlesson.data.state.AddAuthorState
import com.example.navigationlesson.databinding.FragmentAddAuthorBinding
import com.example.navigationlesson.showAlert
import com.example.navigationlesson.showSnackBar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class AddAuthorFragment : Fragment(R.layout.fragment_add_author) {

    lateinit var binding:FragmentAddAuthorBinding

    private val viewModel:AddAuthorViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddAuthorBinding.bind(view)



        binding.btnSaveAuthor.setOnClickListener {
            viewModel.saveAuthor(Database.getDatabase(requireContext()),binding.etName.text.toString(), binding.etSurname.text.toString())
        }

        observeAddAuthorState()


    }

    private fun observeAddAuthorState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.addAuthorState.collect {
                    when (it) {
                        is AddAuthorState.Idle -> {}
                        is AddAuthorState.Success -> {
                            requireContext().showSnackBar(binding.btnSaveAuthor,"${it.author.name} ${it.author.surname} yazari basariyla kaydedildi.")
                            binding.etName.text.clear()
                            binding.etSurname.text.clear()
                        }
                        is AddAuthorState.Error -> {
                            requireContext().showAlert("Uyari","Bir sorun olustu")
                        }
                    }
                }
            }
        }
    }

}