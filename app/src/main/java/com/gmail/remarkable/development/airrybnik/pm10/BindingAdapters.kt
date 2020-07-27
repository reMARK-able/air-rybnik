package com.gmail.remarkable.development.airrybnik.pm10

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.gmail.remarkable.development.airrybnik.database.DatabaseSensorValue
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