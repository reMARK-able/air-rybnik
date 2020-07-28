package com.gmail.remarkable.development.airrybnik.pm10

import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
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