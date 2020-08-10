package com.gmail.remarkable.development.airrybnik.appwidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.gmail.remarkable.development.airrybnik.R

class Pm10AppWidget : AppWidgetProvider() {

    companion object {
        const val INTENT_SENSOR_VALUE = "intentSensorValue"
        const val INTENT_SENSOR_DATE = "intentSensorDate"
    }

    private var date: String? = null
    private var value: String? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        value = intent?.getStringExtra(INTENT_SENSOR_VALUE)
        date = intent?.getStringExtra(INTENT_SENSOR_DATE)
        Log.d("PM10AppWidget", "onReceive: action=${intent?.action} value= $value | date= $date")
        super.onReceive(context, intent)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.d("PM10AppWidget", "onUpdate called")
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, value, date)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    value: String?,
    date: String?
) {
    value?.let {
        Log.d("PM10AppWidget", "updateAppWidget called")
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.pm10_app_widget)
        views.setTextViewText(R.id.widget_howMany_textView, it)
        views.setTextViewText(R.id.widget_when_textView, date)
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }


}