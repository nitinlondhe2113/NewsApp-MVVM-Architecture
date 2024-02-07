package com.nitinlondhe.newsapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nitinlondhe.newsapp.R
import com.nitinlondhe.newsapp.databinding.ActivityMainBinding
import com.nitinlondhe.newsapp.ui.offline.OfflineTopHeadlineActivity
import com.nitinlondhe.newsapp.ui.topheadline.TopHeadlineActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun startTopHeadlinesActivity(view: View) {
        startActivity(Intent(TopHeadlineActivity.getStartIntent(this@MainActivity)))
    }

    fun startOfflineTopHeadlinesActivity(view: View) {
        startActivity(Intent(OfflineTopHeadlineActivity.getStartIntent(this@MainActivity)))
    }

}