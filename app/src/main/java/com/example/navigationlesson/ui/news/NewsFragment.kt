package com.example.navigationlesson.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.navigationlesson.R
import com.example.navigationlesson.data.database.Database
import com.example.navigationlesson.data.state.NewsListState
import com.example.navigationlesson.databinding.FragmentNewsBinding
import com.example.navigationlesson.ui.adapter.NewsAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class NewsFragment : Fragment(R.layout.fragment_news) {

    private lateinit var binding:FragmentNewsBinding
    private val viewModel:NewsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)



        viewModel.getNews(Database.getDatabase(requireContext()))

        observeNewsListState()

    }

    private fun observeNewsListState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.newsListState.collect{
                    when(it){
                        NewsListState.Idle->{}
                        NewsListState.Loading->{
                            //TODO
                        }
                        NewsListState.Empty->{
                            //TODO

                        }
                        is NewsListState.Success->{
                            binding.rvNews.adapter = NewsAdapter(requireContext(),it.news)
                        }
                        is NewsListState.Error->{
                            //TODO

                        }
                    }
                }
            }
        }
    }

}