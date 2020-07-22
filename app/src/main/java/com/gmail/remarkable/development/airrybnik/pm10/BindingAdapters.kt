package com.gmail.remarkable.development.airrybnik.pm10

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.gmail.remarkable.development.airrybnik.network.GiosSensorData
import com.gmail.remarkable.development.airrybnik.network.firstNonNull
import kotlin.math.roundToInt

@BindingAdapter("firstNonNullValueString")
fun TextView.getFirstNonNullValueString(response: GiosSensorData?) {
    response?.firstNonNull()?.let {
        text = it.value?.roundToInt().toString()
    }
}

@BindingAdapter("firstNonNullDateString")
fun TextView.getFirstNonNullDateString(response: GiosSensorData?) {
    response?.firstNonNull()?.let {
        text = it.date.substringBeforeLast(":")
    }
}