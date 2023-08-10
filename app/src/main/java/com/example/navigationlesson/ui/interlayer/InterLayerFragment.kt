package com.example.navigationlesson.ui.interlayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.navigationlesson.R
import com.example.navigationlesson.databinding.FragmentInterLayerBinding


class InterLayerFragment : Fragment(R.layout.fragment_inter_layer) {


    lateinit var binding:FragmentInterLayerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInterLayerBinding.bind(view)



        binding.btnAddAuthor.setOnClickListener {
            findNavController().navigate(R.id.action_interLayerFragment_to_addAuthorFragment)
        }

        binding.btnNews.setOnClickListener {
            findNavController().navigate(R.id.action_interLayerFragment_to_newsFragment)
        }

        binding.btnAddBook.setOnClickListener {
            findNavController().navigate(R.id.action_interLayerFragment_to_addBookFragment)
        }



    }
}