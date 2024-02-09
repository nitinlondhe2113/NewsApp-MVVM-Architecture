package com.nitinlondhe.newsapp.utils

import com.nitinlondhe.newsapp.data.model.Country
import com.nitinlondhe.newsapp.data.model.Language

object AppConstant {

    const val APP_NAME = "NewsApp"
    const val API_KEY = "8094a578ebb841239f30392d9e0fb066"
    const val COUNTRY = "us"
    const val BASE_URL = "https://newsapi.org/v2/"
    const val DATABASE_NAME = "news-database"
    const val INITIAL_PAGE = 1
    const val PAGE_SIZE = 10
    const val DEBOUNCE_TIMEOUT = 300L
    const val MIN_SEARCH_CHAR = 3
    const val NEWS_BY_SOURCES = "sources"
    const val NEWS_BY_COUNTRY = "country"
    const val NEWS_BY_LANGUAGE = "language"
    const val SOURCE_ID = "sourceId"
    const val COUNTRY_ID = "countryId"
    const val LANGUAGE_ID = "languageId"

    //WorkManager and Notification
    const val UNIQUE_WORK_NAME = "newsAppPeriodicWork"
    const val MORNING_UPDATE_TIME = 5
    const val NOTIFICATION_ID = 1
    const val NOTIFICATION_CHANNEL_ID = "news_channel"
    const val NOTIFICATION_CHANNEL_NAME = "News"
    const val NOTIFICATION_CONTENT_TITLE = "News"
    const val NOTIFICATION_CONTENT_TEXT = "Check out the latest news ..."

    val COUNTRIES = listOf(
        Country("ae", "United Arab Emirates"),
        Country("ar", "Argentina"),
        Country("at", "Austria"),
        Country("be", "Belgium"),
        Country("bg", "Bulgaria"),
        Country("br", "Brazil"),
        Country("ca", "Canada"),
        Country("ch", "Switzerland"),
        Country("cn", "China"),
        Country("co", "Colombia"),
        Country("cu", "Cuba"),
        Country("cz", "Czechia"),
        Country("de", "Germany"),
        Country("eg", "Egypt"),
        Country("fr", "France"),
        Country("gb", "United Kingdom of Great Britain and Northern Ireland"),
        Country("gr", "Greece"),
        Country("hk", "Hong Kong"),
        Country("hu", "Hungary"),
        Country("id", "Indonesia"),
        Country("ie", "Ireland"),
        Country("il", "Israel"),
        Country("in", "India"),
        Country("it", "Italy"),
        Country("jp", "Japan"),
        Country("kr", "Korea"),
        Country("lt", "Lithuania"),
        Country("lv", "Latvia"),
        Country("ma", "Morocco"),
        Country("mx", "Mexico"),
        Country("my", "Malaysia"),
        Country("ng", "Nigeria"),
        Country("nl", "Netherlands"),
        Country("no", "Norway"),
        Country("nz", "New Zealand"),
        Country("ph", "Philippines"),
        Country("pl", "Poland"),
        Country("pt", "Portugal"),
        Country("ro", "Romania"),
        Country("rs", "Serbia"),
        Country("ru", "Russian Federation"),
        Country("sa", "Saudi Arabia"),
        Country("se", "Sweden"),
        Country("sg", "Singapore"),
        Country("si", "Slovenia"),
        Country("sk", "Slovakia"),
        Country("th", "Thailand"),
        Country("tr", "Turkiye"),
        Country("tw", "Taiwan, Province of China"),
        Country("ua", "Ukraine"),
        Country("us", "United States of America"),
        Country("ve", "Venezuela"),
        Country("za", "South Africa")
    )

    val LANGUAGES = listOf(
        Language("ar", "Arabic"),
        Language("de", "German"),
        Language("en", "English"),
        Language("fr", "French"),
        Language("he", "Hebrew"),
        Language("it", "Italian"),
        Language("nl", "Dutch"),
        Language("no", "Norwegian"),
        Language("pt", "Portuguese"),
        Language("ru", "Russian"),
        Language("sv", "Swedish"),
        Language("zh", "Chinese")
    )
}