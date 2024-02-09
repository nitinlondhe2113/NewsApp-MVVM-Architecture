package com.nitinlondhe.newsapp.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nitinlondhe.newsapp.R
import com.nitinlondhe.newsapp.data.api.NetworkService
import com.nitinlondhe.newsapp.data.local.DatabaseService
import com.nitinlondhe.newsapp.data.model.topheadlines.apiArticleListToArticleList
import com.nitinlondhe.newsapp.ui.MainActivity
import com.nitinlondhe.newsapp.utils.AppConstant
import com.nitinlondhe.newsapp.utils.AppConstant.NOTIFICATION_ID
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class FetchTopHeadlinesWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        lateinit var result: Result
        kotlin.runCatching {
            val articles = networkService.getTopHeadlines(AppConstant.COUNTRY)
                .apiArticles.apiArticleListToArticleList(AppConstant.COUNTRY)
            databaseService.deleteAndInsertAllTopHeadlinesArticles(articles, AppConstant.COUNTRY)
        }.onSuccess {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sendNotification()
            }
            result = Result.success()
        }.onFailure {
            result = Result.retry()
        }
        return result
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification() {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create a notification channel
        val channel = NotificationChannel(
            AppConstant.NOTIFICATION_CHANNEL_ID,
            AppConstant.NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        // Create an Intent for the NewsActivity
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("notification_id", NOTIFICATION_ID)

        // Create a PendingIntent
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Build the notification
        val notification =
            NotificationCompat.Builder(applicationContext, AppConstant.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(AppConstant.NOTIFICATION_CONTENT_TEXT)
                .setContentTitle(AppConstant.NOTIFICATION_CONTENT_TITLE)
                .setContentIntent(pendingIntent)
                .build()

        // Show the notification
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}