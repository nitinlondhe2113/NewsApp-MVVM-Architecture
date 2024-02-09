package com.nitinlondhe.newsapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nitinlondhe.newsapp.R
import com.nitinlondhe.newsapp.ui.base.Route

@Composable
fun HomeScreenRoute(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeScreen(navController)
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.LightGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        TitleButton(stringResource(R.string.top_headlines), navController, Route.TopHeadline.name)

        TitleButton(stringResource(R.string.offline_top_headlines), navController, Route.OfflineTopHeadline.name)

        TitleButton(stringResource(R.string.pagination_top_headlines), navController, Route.PaginationTopHeadline.name)

        TitleButton(stringResource(R.string.news_sources), navController, Route.NewsSources.name)

        TitleButton(stringResource(R.string.coutries), navController, Route.CountryList.name)

        TitleButton(stringResource(R.string.language), navController, Route.LanguageList.name)

        TitleButton(stringResource(R.string.search), navController, Route.Search.name)

    }
}

@Composable
fun TitleButton(title: String, navController: NavController, routeName: String) {
    Button(
        onClick = {
            navController.navigate(route = routeName)
        },
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(start = 20.dp, top = 5.dp, end = 20.dp, bottom = 5.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}