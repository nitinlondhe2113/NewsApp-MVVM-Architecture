package com.nitinlondhe.newsapp.utils.logger

import android.util.Log

class AppLogger : Logger {
    override fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }
}