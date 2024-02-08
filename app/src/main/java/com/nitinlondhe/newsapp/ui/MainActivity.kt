package com.nitinlondhe.newsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.nitinlondhe.newsapp.ui.base.NewsNavHost
import com.nitinlondhe.newsapp.ui.theme.NewsAppTheme
import com.nitinlondhe.newsapp.ui.theme.gray40
import com.nitinlondhe.newsapp.utils.AppConstant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                Scaffold(topBar = {
                    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = Color.White
                    ), title = { Text(text = AppConstant.APP_NAME) })
                }) { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding)
                            .background(gray40),
                    ) {
                        NewsNavHost()
                    }
                }

            }
        }
    }
}