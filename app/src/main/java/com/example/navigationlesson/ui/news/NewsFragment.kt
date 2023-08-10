package com.example.navigationlesson.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Adapter
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.navigationlesson.R
import com.example.navigationlesson.data.database.Database
import com.example.navigationlesson.data.database.entity.News
import com.example.navigationlesson.data.state.AdapterState
import com.example.navigationlesson.data.state.NewsListState
import com.example.navigationlesson.databinding.FragmentNewsBinding
import com.example.navigationlesson.showAlert
import com.example.navigationlesson.showSnackBar
import com.example.navigationlesson.ui.adapter.NewsAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class NewsFragment : Fragment(R.layout.fragment_news) {

    private lateinit var binding:FragmentNewsBinding
    private val viewModel:NewsViewModel by activityViewModels()
    lateinit var adapter:NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)



        viewModel.getNews(Database.getDatabase(requireContext()))

        observeNewsListState()
        observeAdapterState()

    }

    private fun observeAdapterState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.adapterState.collect {
                    when(it) {
                        is AdapterState.Removed -> {
                            adapter.notifyItemRemoved(it.index)
                            requireContext().showSnackBar(binding.rvNews, "haber silindi")
                        }

                        else -> {}
                    }
                }
            }
        }
    }


    companion object{
        const val NEWS_ID_KEY = "news_id_key"
    }

    private fun onClick(news:News) {
        findNavController().navigate(R.id.action_newsFragment_to_newsDetailFragment, bundleOf(NEWS_ID_KEY to news.id))
    }

    private fun onRemove(news:News) {
        viewModel.remove(news, Database.getDatabase(requireContext()))
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
                            requireContext().showAlert("Uyari","Haber Bulunamadi!")

                        }
                        is NewsListState.Success->{
                            adapter = NewsAdapter(requireContext(),it.news,this@NewsFragment::onClick,this@NewsFragment::onRemove)
                            binding.rvNews.adapter = adapter
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