package com.gmail.remarkable.development.airrybnik.util

import android.content.Context
import android.content.res.ColorStateList
import android.widget.RemoteViews
import androidx.core.content.ContextCompat
import com.gmail.remarkable.development.airrybnik.R
import kotlin.math.roundToInt

/**
 * Returns appropriate color based on the progress value.
 */
fun getIndicatorProgressColor(progress: Int): Int {
    return when (progress) {
        in 0..9 -> R.color.colorIndicator_0
        in 10..19 -> R.color.colorIndicator_10
        in 20..29 -> R.color.colorIndicator_20
        in 30..39 -> R.color.colorIndicator_30
        in 40..49 -> R.color.colorIndicator_40
        in 50..59 -> R.color.colorIndicator_50
        in 60..69 -> R.color.colorIndicator_60
        in 70..79 -> R.color.colorIndicator_70
        in 80..89 -> R.color.colorIndicator_80
        in 90..99 -> R.color.colorIndicator_90
        in 100..160 -> R.color.colorIndicator_100
        in 161..220 -> R.color.colorIndicator_index_sufficient
        in 221..300 -> R.color.colorIndicator_index_bad
        else -> R.color.colorIndicator_index_veryBad
    }
}

/**
 * Returns RemoteViews to update appWidget.
 */
fun setupRemoteViewsForUpdate(value: Double, date: String?, context: Context): RemoteViews {
    return RemoteViews(context.packageName, R.layout.chart_layout_v_app_widget).apply {
        val percentage = value.roundToInt().times(100).div(50)
        val setTintMethod = RemoteViews::class.java.getMethod(
            "setProgressTintList",
            Int::class.java,
            ColorStateList::class.java
        )
        val progressBarColor =
            ContextCompat.getColor(context, getIndicatorProgressColor(percentage))
        setTintMethod.invoke(
            this,
            R.id.widget_percentage_progressbar,
            ColorStateList.valueOf(progressBarColor)
        )
        setTextViewText(
            R.id.widget_percentage_textView,
            context.getString(R.string.percentage_value, percentage)
        )
        setTextViewText(R.id.widget_label_textView, "PM10")
        setProgressBar(R.id.widget_percentage_progressbar, 100, percentage, false)
    }
}