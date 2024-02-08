package com.nitinlondhe.newsapp.ui.base

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nitinlondhe.newsapp.home.HomeScreenRoute
import com.nitinlondhe.newsapp.ui.topheadline.TopHeadlineRoute

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
        composable(route = Route.TopHeadline.name) {
            TopHeadlineRoute(onNewsClick = {
                openCustomChromeTab(context, it)
            })
        }
    }
}

fun openCustomChromeTab(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}