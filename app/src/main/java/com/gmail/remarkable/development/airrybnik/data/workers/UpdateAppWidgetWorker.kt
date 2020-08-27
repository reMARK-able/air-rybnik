package com.gmail.remarkable.development.airrybnik.data.workers

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gmail.remarkable.development.airrybnik.appwidget.Pm10AppWidget
import com.gmail.remarkable.development.airrybnik.data.Repository
import com.gmail.remarkable.development.airrybnik.util.setupRemoteViewsForUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class UpdateAppWidgetWorker @WorkerInject constructor(
    @Assisted private val appContext: Context,
    @Assisted params: WorkerParameters,
    private val repository: Repository
) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "updateUpWidgetWorker"
    }

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                repository.getLatest()?.let {
                    val valueString = it.value?.roundToInt().toString()
                    val dateString = it.date.substringBeforeLast(":")
                    notifyAppWidget(valueString, dateString)
                }
                Result.success()
            } catch (e: Throwable) {
                Log.d("UpdateAppWidgetWorker", "retry: ${e.message}; ${e.cause}")
                Result.retry()
            }
        }
    }

    private fun notifyAppWidget(value: String, date: String) {
        val views = setupRemoteViewsForUpdate(value, date, appContext)
        val appWidgetManager = AppWidgetManager.getInstance(appContext)
        appWidgetManager.updateAppWidget(
            ComponentName(appContext, Pm10AppWidget::class.java),
            views
        )
    }
}