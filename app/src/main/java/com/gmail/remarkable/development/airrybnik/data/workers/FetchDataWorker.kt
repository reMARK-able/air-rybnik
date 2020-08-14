package com.gmail.remarkable.development.airrybnik.data.workers

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
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
        val appWidgetManager = AppWidgetManager.getInstance(appContext)
        val widgetIds =
            appWidgetManager.getAppWidgetIds(ComponentName(appContext, Pm10AppWidget::class.java))

        val intent = Intent(appContext, Pm10AppWidget::class.java).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            putExtra(Pm10AppWidget.INTENT_SENSOR_DATE, date)
            putExtra(Pm10AppWidget.INTENT_SENSOR_VALUE, value)
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds)
        }

        appContext.sendBroadcast(intent)

    }
}