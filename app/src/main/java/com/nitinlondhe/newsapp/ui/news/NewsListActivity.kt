package com.nitinlondhe.newsapp.ui.news

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
import com.nitinlondhe.newsapp.databinding.ActivityNewsListBinding
import com.nitinlondhe.newsapp.di.component.DaggerActivityComponent
import com.nitinlondhe.newsapp.di.module.ActivityModule
import com.nitinlondhe.newsapp.ui.base.UiState
import com.nitinlondhe.newsapp.utils.AppConstant
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsListActivity : AppCompatActivity() {

    @Inject
    lateinit var newsListViewModel: NewsListViewModel

    @Inject
    lateinit var newsListAdapter: NewsListAdapter

    private lateinit var binding: ActivityNewsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = newsListAdapter
        }

        binding.includeLayout.tryAgainBtn.setOnClickListener {
            getIntentData()
        }

        getIntentData()
    }

    private fun getIntentData() {
        intent.extras?.apply {
            val newsType = getString(EXTRA_NEWS_TYPE)
            newsType?.let { type ->
                when (type) {
                    AppConstant.NEWS_BY_SOURCES -> {
                        val source = getString(EXTRA_NEWS_SOURCE)
                        source?.let {
                            newsListViewModel.fetchNewsBySources(it)
                        }
                    }
                    AppConstant.NEWS_BY_COUNTRY -> {
                        val countryId = getString(EXTRA_COUNTRY_ID)
                        countryId?.let {
                            newsListViewModel.fetchNewsByCountry(it)
                        }
                    }
                    AppConstant.NEWS_BY_LANGUAGE -> {
                        val languageId = getString(EXTRA_LANGUAGE_LIST)
                        languageId?.let {
                            newsListViewModel.fetchNewsByLanguage(it)
                        }
                    }
                }
            }
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsListViewModel.newUiState.collect {
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
                            Toast.makeText(this@NewsListActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }

                    }
                }
            }
        }
    }

    private fun renderList(articleList: List<Article>) {
        newsListAdapter.addArticles(articleList)
        newsListAdapter.notifyDataSetChanged()
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }

    companion object {

        private const val EXTRA_NEWS_SOURCE = "EXTRA_NEWS_SOURCE"
        private const val EXTRA_NEWS_TYPE = "EXTRA_NEWS_TYPE"
        private const val EXTRA_COUNTRY_ID = "EXTRA_COUNTRY_ID"
        private const val EXTRA_LANGUAGE_LIST = "EXTRA_LANGUAGE_LIST"

        fun getStartIntent(
            context: Context,
            newsSource: String? = "",
            countryID: String? = "",
            newsType: String,
            langList: String? = "",
        ): Intent {
            return Intent(context, NewsListActivity::class.java).apply {
                putExtra(EXTRA_NEWS_TYPE, newsType)
                putExtra(EXTRA_NEWS_SOURCE, newsSource)
                putExtra(EXTRA_COUNTRY_ID, countryID)
                putExtra(EXTRA_LANGUAGE_LIST, langList)
            }
        }
    }
}