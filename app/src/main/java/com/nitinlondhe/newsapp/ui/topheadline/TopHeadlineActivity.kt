package com.nitinlondhe.newsapp.ui.topheadline

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nitinlondhe.newsapp.data.local.entity.Article
import com.nitinlondhe.newsapp.databinding.ActivityTopHeadlineBinding
import com.nitinlondhe.newsapp.ui.base.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TopHeadlineActivity : AppCompatActivity() {

    lateinit var topHeadlineViewModel: TopHeadlineViewModel

    @Inject
    lateinit var topHeadlineAdapter: TopHeadlineAdapter

    private lateinit var binding: ActivityTopHeadlineBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopHeadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupUI()
        setupObserver()
    }

    private fun setupViewModel() {
        topHeadlineViewModel = ViewModelProvider(this)[TopHeadlineViewModel::class.java]
    }

    private fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = topHeadlineAdapter
        }

        binding.includeLayout.tryAgainBtn.setOnClickListener {
            topHeadlineViewModel.startFetchingArticles()
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                topHeadlineViewModel.topHeadlineUiState.collect {
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
                            Toast.makeText(this@TopHeadlineActivity, it.message, Toast.LENGTH_LONG)
                                .show()
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

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, TopHeadlineActivity::class.java)
        }
    }
}