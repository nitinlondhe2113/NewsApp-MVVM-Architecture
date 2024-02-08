package com.nitinlondhe.newsapp.ui.sources

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nitinlondhe.newsapp.data.local.entity.NewsSources
import com.nitinlondhe.newsapp.databinding.ActivityNewsSourcesBinding
import com.nitinlondhe.newsapp.ui.base.UiState
import com.nitinlondhe.newsapp.ui.news.NewsListActivity
import com.nitinlondhe.newsapp.utils.AppConstant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewsSourcesActivity : AppCompatActivity() {

    lateinit var newsSourcesViewModel : NewsSourcesViewModel

    @Inject
    lateinit var newsSourceAdapter: NewsSourceAdapter

    private lateinit var binding: ActivityNewsSourcesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsSourcesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupUI()
        setupObserver()
    }

    private fun setupViewModel() {
        newsSourcesViewModel = ViewModelProvider(this)[NewsSourcesViewModel::class.java]
    }

    private fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter=newsSourceAdapter
        }

        binding.includeLayout.tryAgainBtn.setOnClickListener {
            newsSourcesViewModel.startFetchingSource()
        }

        newsSourceAdapter.itemClickListener = { _, newsSource ->
            val sources = newsSource as NewsSources
            startActivity(
                NewsListActivity.getStartIntent(
                    context = this@NewsSourcesActivity,
                    newsSource = sources.sourceId,
                    newsType = AppConstant.NEWS_BY_SOURCES
                )
            )
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                newsSourcesViewModel.newsSourceUiState.collect {
                    when(it) {
                        is UiState.Success ->{
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
                            binding.progressBar.visibility = View.VISIBLE
                            binding.includeLayout.errorLayout.visibility = View.VISIBLE
                            Toast.makeText(this@NewsSourcesActivity,it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(sourceList: List<NewsSources>) {
        newsSourceAdapter.addSources(sourceList)
        newsSourceAdapter.notifyDataSetChanged()
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, NewsSourcesActivity::class.java)
        }
    }

}