package com.example.navigationlesson.ui.newsDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.navigationlesson.R
import com.example.navigationlesson.data.database.Database
import com.example.navigationlesson.data.state.NewsDetailState
import com.example.navigationlesson.databinding.FragmentNewsDetailBinding
import com.example.navigationlesson.showToast
import com.example.navigationlesson.ui.news.NewsFragment
import kotlinx.coroutines.launch


class NewsDetailFragment : Fragment(R.layout.fragment_news_detail) {


    private lateinit var binding:FragmentNewsDetailBinding
    private val viewModel: NewsDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            binding = FragmentNewsDetailBinding.bind(view)



           arguments?.getInt(NewsFragment.NEWS_ID_KEY,-1)?.let {newsId->
               viewModel.getNewsById(Database.getDatabase(requireContext()), newsId)
           }



            observeNewsDetailState()



    }

    private fun observeNewsDetailState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.newsDetailState.collect{
                    when(it){
                        is NewsDetailState.Idle -> {}
                        is NewsDetailState.Success -> {
                            with(it.news) {
                                binding.ivNews.load(imageUrl)
                                binding.tvTitle.text = title
                                binding.tvContent.text = content
                            }
                        }
                        is NewsDetailState.Error -> {
                            requireContext().showToast("Bir sorun olustu")
                        }
                    }
                }
            }
        }
    }


}