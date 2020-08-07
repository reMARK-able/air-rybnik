package com.gmail.remarkable.development.airrybnik.data.workers

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.widget.RemoteViews
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gmail.remarkable.development.airrybnik.R
import com.gmail.remarkable.development.airrybnik.appwidget.Pm10AppWidget
import com.gmail.remarkable.development.airrybnik.data.Repository
import com.gmail.remarkable.development.airrybnik.util.sendNewDataNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.math.roundToInt

class FetchDataWorker @WorkerInject constructor(
    @Assisted val appContext: Context,
    @Assisted params: WorkerParameters,
    private val repository: Repository
) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "Fetch from network"
    }

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                repository.refreshData()?.let {
                    val valueString = it.value?.roundToInt().toString()
                    val dateString = it.date.substringBeforeLast(":")
                    // Sends notification on every data fetch. (only for testing purposes).
                    sendNewDataNotification(appContext, valueString)
                    notifyAppWidget(valueString, dateString)
                }

                Result.success()
            } catch (e: HttpException) {
                Result.retry()
            }
        }

    }

    private fun notifyAppWidget(value: String, date: String) {
        val views = RemoteViews(appContext.packageName, R.layout.pm10_app_widget)
        views.setTextViewText(R.id.widget_howMany_textView, value)
        views.setTextViewText(R.id.widget_when_textView, date)
        val appWidgetManager = AppWidgetManager.getInstance(appContext)
        appWidgetManager.updateAppWidget(
            ComponentName(appContext, Pm10AppWidget::class.java),
            views
        )
    }
}