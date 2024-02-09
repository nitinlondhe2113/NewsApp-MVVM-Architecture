package com.nitinlondhe.newsapp.ui.sources

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nitinlondhe.newsapp.R
import com.nitinlondhe.newsapp.data.local.entity.NewsSources
import com.nitinlondhe.newsapp.ui.base.ShowError
import com.nitinlondhe.newsapp.ui.base.ShowLoading
import com.nitinlondhe.newsapp.ui.base.UiState
import com.nitinlondhe.newsapp.ui.theme.purple

@Composable
fun NewsSourcesRoute(
    onNewsClick: (sourceId: String) -> Unit,
    newsSourceViewModel: NewsSourcesViewModel = hiltViewModel()
) {

    val newsSourceUiState: UiState<List<NewsSources>> by newsSourceViewModel.newsSourceUiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleText()
        SourcesScreen(newsSourceUiState, onNewsClick, onRetryClick = {
            newsSourceViewModel.startFetchingSource()
        })
    }
}

@Composable
fun SourcesScreen(
    uiState: UiState<List<NewsSources>>,
    onNewsClick: (sourceId: String) -> Unit,
    onRetryClick: () -> Unit
) {
    when (uiState) {
        is UiState.Success -> {
            SourceList(uiState.data, onNewsClick)
        }

        is UiState.Loading -> {
            ShowLoading()
        }

        is UiState.Error -> {
            ShowError(text = uiState.message) {
                onRetryClick()
            }
        }
    }
}

@Composable
fun SourceList(sources: List<NewsSources>, onNewsClick: (sourceId: String) -> Unit) {
    LazyColumn {
        items(sources.size) { index ->
            Source(source = sources[index], onNewsClick)
        }
    }
}

@Composable
fun Source(source: NewsSources, onNewsClick: (source: String) -> Unit) {
    Button(
        onClick = { source.sourceId.let { onNewsClick(it) } },
        colors = ButtonDefaults.buttonColors(
            containerColor = purple
        ),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(start = 20.dp, top = 5.dp, end = 20.dp, bottom = 5.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = source.name,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun TitleText() {
    Text(
        text = stringResource(R.string.news_sources),
        style = MaterialTheme.typography.labelLarge,
        fontSize = 20.sp,
        color = Color.Black,
        modifier = Modifier
            .padding(4.dp)
    )
}
