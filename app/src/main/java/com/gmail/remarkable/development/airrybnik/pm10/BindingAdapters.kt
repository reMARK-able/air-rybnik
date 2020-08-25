package com.gmail.remarkable.development.airrybnik.pm10

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.gmail.remarkable.development.airrybnik.R
import com.gmail.remarkable.development.airrybnik.data.database.DatabaseSensorValue
import com.gmail.remarkable.development.airrybnik.util.getIndicatorProgressColor
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
        val percentage = (response.value?.roundToInt()?.times(100)?.div(50)) ?: 0
        val percentAnimator = ObjectAnimator.ofInt(this, "progress", 0, percentage)
        val fromColor = ContextCompat.getColor(context, R.color.colorIndicator_0)
        val toColor = ContextCompat.getColor(context, getIndicatorProgressColor(percentage))
        val colorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), fromColor, toColor)
        colorAnimator.duration = 600
        colorAnimator.addUpdateListener {
            progressTintList = ColorStateList.valueOf(it.animatedValue as Int)
        }
        percentAnimator.interpolator = LinearInterpolator()
        percentAnimator.duration = 600
        AnimatorSet().apply {
            playTogether(percentAnimator, colorAnimator)
            start()
        }
    }
}

@BindingAdapter("percentageString")
fun TextView.setPercentageString(response: DatabaseSensorValue?) {
    response?.let {
        val percentage = (response.value?.roundToInt()?.times(100)?.div(50)) ?: 0
        text = context.getString(R.string.percentage_value, percentage)
    }
}