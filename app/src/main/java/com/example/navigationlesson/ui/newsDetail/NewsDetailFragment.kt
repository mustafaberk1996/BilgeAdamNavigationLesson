package com.example.navigationlesson.ui.newsDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.navigationlesson.HomeViewModel
import com.example.navigationlesson.R
import com.example.navigationlesson.databinding.FragmentNewsDetailBinding
import kotlinx.coroutines.launch


class NewsDetailFragment : Fragment(R.layout.fragment_news_detail) {


    private lateinit var binding:FragmentNewsDetailBinding
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsDetailBinding.bind(view)


        //observeHomeUIState()



    }

    private fun observeHomeUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.homeUiState.collect{
                    when(it){
                        HomeViewModel.HomeUiState.Idle ->{}
                        HomeViewModel.HomeUiState.Second ->{
                            Toast.makeText(requireContext(),"clicked!",Toast.LENGTH_SHORT).show()
                            //findNavController().navigate(R.id.action_homeFragment_to_secondFragment)
                        }
                    }
                }
            }
        }
    }


}