package com.nitinlondhe.newsapp.ui.pagination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nitinlondhe.newsapp.databinding.TopHeadlineItemLayoutBinding
import com.nitinlondhe.newsapp.utils.ItemClickListener
import com.bumptech.glide.Glide
import com.nitinlondhe.newsapp.data.model.topheadlines.ApiArticle

class PaginationTopHeadlineAdapter :
    PagingDataAdapter<ApiArticle, PaginationTopHeadlineAdapter.DataViewHolder>(UIMODEL_COMPARATOR) {

    lateinit var itemClickListener: ItemClickListener<Any>

    class DataViewHolder(private val binding: TopHeadlineItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: ApiArticle, itemClickListener: ItemClickListener<Any>) {
            binding.txtTitle.text = article.title
            binding.txtDescription.text = article.description
            binding.txtSource.text = article.apiSource.name

            Glide.with(binding.imgBanner.context).load(article.imageUrl)
                .into(binding.imgBanner)

            itemView.setOnClickListener {
                itemClickListener(bindingAdapterPosition, article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        TopHeadlineItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val article = getItem(position)
        article?.let {
            holder.bind(it, itemClickListener)
        }

    }

    companion object {
        private val UIMODEL_COMPARATOR = object : DiffUtil.ItemCallback<ApiArticle>() {
            override fun areItemsTheSame(oldItem: ApiArticle, newItem: ApiArticle): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: ApiArticle, newItem: ApiArticle): Boolean {
                return oldItem == newItem
            }

        }
    }

}