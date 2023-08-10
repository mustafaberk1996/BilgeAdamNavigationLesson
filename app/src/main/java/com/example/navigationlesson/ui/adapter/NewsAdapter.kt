package com.example.navigationlesson.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.navigationlesson.R
import com.example.navigationlesson.data.database.entity.News
import com.example.navigationlesson.databinding.NewsListItemBinding
import okhttp3.internal.addHeaderLenient

class NewsAdapter(private val context: Context, private val newsList:List<News>, val onClick:(news:News)->Unit,val onRemove:(news:News)->Unit): RecyclerView.Adapter<NewsAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            NewsListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val news = newsList[position]
        holder.tvContent.text = news.content
        holder.tvTitle.text = news.title
        holder.ivImg.load(news.imageUrl){
            placeholder(R.drawable.baseline_image_24)
            error(R.drawable.baseline_cloud_off_24)
        }

        holder.ivDelete.setOnClickListener {
            onRemove(news)
        }

        holder.itemView.setOnClickListener {
            onClick(news)
        }
    }

    class CustomViewHolder(binding: NewsListItemBinding):RecyclerView.ViewHolder(binding.root) {
        val tvTitle = binding.tvTitle
        val tvContent = binding.tvContent
        val ivImg = binding.ivNews
        val ivDelete = binding.ivDelete
    }

}