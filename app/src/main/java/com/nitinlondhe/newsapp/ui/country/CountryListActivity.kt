package com.nitinlondhe.newsapp.ui.country

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
import com.nitinlondhe.newsapp.data.model.Country
import com.nitinlondhe.newsapp.databinding.ActivityCountryListBinding
import com.nitinlondhe.newsapp.ui.base.UiState
import com.nitinlondhe.newsapp.ui.news.NewsListActivity
import com.nitinlondhe.newsapp.utils.AppConstant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CountryListActivity : AppCompatActivity() {

    lateinit var countryListViewModel: CountryListViewModel

    @Inject
    lateinit var countryListAdapter: CountryListAdapter

    private lateinit var binding: ActivityCountryListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupUI()
        setupObserver()
    }

    private fun setupViewModel() {
        countryListViewModel = ViewModelProvider(this)[CountryListViewModel::class.java]
    }

    private fun setupUI() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = countryListAdapter
        }

        binding.includeLayout.tryAgainBtn.setOnClickListener {
            countryListViewModel.fetchCountry()
        }

        countryListAdapter.itemClickListener = { _, countryList ->
            val country = countryList as Country
            startActivity(
                NewsListActivity.getStartIntent(
                    context = this@CountryListActivity,
                    countryID = country.id,
                    newsType = AppConstant.NEWS_BY_COUNTRY
                )
            )
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                countryListViewModel.countryUiState.collect {
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
                            binding.progressBar.visibility = View.VISIBLE
                            binding.includeLayout.errorLayout.visibility = View.VISIBLE
                            Toast.makeText(this@CountryListActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(sourceList: List<Country>) {
        countryListAdapter.addCountry(sourceList)
        countryListAdapter.notifyDataSetChanged()
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, CountryListActivity::class.java)
        }
    }
}