package com.nitinlondhe.newsapp.ui.pagination

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nitinlondhe.newsapp.data.model.topheadlines.ApiArticle
import com.nitinlondhe.newsapp.databinding.ActivityPaginationTopHeadlineBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PaginationTopHeadlineActivity : AppCompatActivity() {

    lateinit var paginationTopHeadlineViewModel: PaginationTopHeadlineViewModel

    @Inject
    lateinit var topHeadlineAdapter: PaginationTopHeadlineAdapter

    private lateinit var binding: ActivityPaginationTopHeadlineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaginationTopHeadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupUI()
        setupObserver()
    }

    private fun setupViewModel() {
        paginationTopHeadlineViewModel = ViewModelProvider(this)[PaginationTopHeadlineViewModel::class.java]
    }

    private fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = topHeadlineAdapter
        }
        topHeadlineAdapter.itemClickListener = { _, data ->
            val article = data as ApiArticle
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this@PaginationTopHeadlineActivity, Uri.parse(article.url))
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                paginationTopHeadlineViewModel.topHeadlineUiState.collect { pagingData ->
                    binding.progressBar.visibility = View.GONE
                    binding.includeLayout.errorLayout.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    topHeadlineAdapter.submitData(pagingData)
                }
            }
        }

    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, PaginationTopHeadlineActivity::class.java)
        }
    }
}