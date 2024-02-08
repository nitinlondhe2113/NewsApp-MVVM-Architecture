package com.nitinlondhe.newsapp.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nitinlondhe.newsapp.home.HomeScreenRoute

sealed class Route(val name: String) {

    object HomeScreen : Route("homescreen")

    object TopHeadline : Route("topheadline")
    object PaginationTopHeadline : Route("paginationtopheadline")

    object OfflineTopHeadline : Route("offlinetopheadline")
    object NewsSources : Route("newssources")
    object LanguageList : Route("languagelist")
    object CountryList : Route("countrylist")
    object Search : Route("search")

}

@Composable
fun NewsNavHost() {

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = Route.HomeScreen.name
    ) {
        composable(route = Route.HomeScreen.name) {
            HomeScreenRoute(navController)
        }

    }
}