package com.gmail.remarkable.development.airrybnik.pm10

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.gmail.remarkable.development.airrybnik.R
import com.gmail.remarkable.development.airrybnik.data.database.DatabaseSensorValue
import kotlin.math.roundToInt

@BindingAdapter("valueString")
fun TextView.getValueString(response: DatabaseSensorValue?) {
    response?.let {
        text = it.value?.roundToInt().toString()
    }
}

@BindingAdapter("dateString")
fun TextView.getDateString(response: DatabaseSensorValue?) {
    response?.let {
        text = it.date.substringBeforeLast(":")
    }
}

@BindingAdapter("hideRefreshButton")
fun ImageButton.hideRefreshButton(isLoading: Boolean) {
    isLoading.let {
        visibility = when {
            isLoading -> View.INVISIBLE
            else -> View.VISIBLE
        }
    }
}

@BindingAdapter("showSpinner")
fun ProgressBar.showSpinner(isLoading: Boolean) {
    isLoading.let {
        visibility = when {
            isLoading -> View.VISIBLE
            else -> View.GONE
        }
    }
}

@BindingAdapter("chartProgress")
fun ProgressBar.setChartProgress(response: DatabaseSensorValue?) {
    response?.let {
        val percentage = (response.value?.times(100)?.div(50))?.toInt() ?: 0
        val animator = ObjectAnimator.ofInt(this, "progress", 0, percentage)
        animator.interpolator = LinearInterpolator()
        animator.duration = 600
        animator.start()
    }
}

@BindingAdapter("percentageString")
fun TextView.setPercentageString(response: DatabaseSensorValue?) {
    response?.let {
        val percentage = (response.value?.times(100)?.div(50))?.toInt() ?: 0
        text = context.getString(R.string.percentage_value, percentage)
    }
}