package com.nitinlondhe.newsapp.ui.pagination

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nitinlondhe.newsapp.NewsApplication
import com.nitinlondhe.newsapp.data.model.topheadlines.ApiArticle
import com.nitinlondhe.newsapp.databinding.ActivityPaginationTopHeadlineBinding
import com.nitinlondhe.newsapp.di.component.DaggerActivityComponent
import com.nitinlondhe.newsapp.di.module.ActivityModule
import kotlinx.coroutines.launch
import javax.inject.Inject

class PaginationTopHeadlineActivity : AppCompatActivity() {

    @Inject
    lateinit var paginationTopHeadlineViewModel: PaginationTopHeadlineViewModel

    @Inject
    lateinit var topHeadlineAdapter: PaginationTopHeadlineAdapter

    private lateinit var binding: ActivityPaginationTopHeadlineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityPaginationTopHeadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()
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

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build(). inject(this)
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, PaginationTopHeadlineActivity::class.java)
        }
    }
}