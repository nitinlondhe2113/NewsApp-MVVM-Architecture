package com.nitinlondhe.newsapp.ui.offline

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nitinlondhe.newsapp.NewsApplication
import com.nitinlondhe.newsapp.data.local.entity.Article
import com.nitinlondhe.newsapp.databinding.ActivityOfflineTopHeadlineBinding
import com.nitinlondhe.newsapp.di.component.DaggerActivityComponent
import com.nitinlondhe.newsapp.di.module.ActivityModule
import com.nitinlondhe.newsapp.ui.base.UiState
import com.nitinlondhe.newsapp.ui.topheadline.TopHeadlineAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class OfflineTopHeadlineActivity : AppCompatActivity() {

    @Inject
    lateinit var offlineTopHeadlineViewModel: OfflineTopHeadlineViewModel

    @Inject
    lateinit var topHeadlineAdapter: TopHeadlineAdapter

    private lateinit var binding: ActivityOfflineTopHeadlineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityOfflineTopHeadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = topHeadlineAdapter
        }

        binding.includeLayout.tryAgainBtn.setOnClickListener {
            offlineTopHeadlineViewModel.startFetchingArticles()
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                offlineTopHeadlineViewModel.topHeadlineUiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            renderList(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.includeLayout.errorLayout.visibility = View.GONE
                        }

                        is UiState.Loading -> {
                            binding.apply {
                                progressBar.visibility = View.VISIBLE
                                recyclerView.visibility = View.GONE
                                binding.includeLayout.errorLayout.visibility = View.GONE
                            }
                        }

                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.includeLayout.errorLayout.visibility = View.VISIBLE
                            Toast.makeText(this@OfflineTopHeadlineActivity, it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(articleList: List<Article>) {
        topHeadlineAdapter.addArticles(articleList)
        topHeadlineAdapter.notifyDataSetChanged()
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, OfflineTopHeadlineActivity::class.java)
        }
    }
}