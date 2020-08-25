package com.gmail.remarkable.development.airrybnik.util

import com.gmail.remarkable.development.airrybnik.R

/**
 * Returns appropriate color based on the progress value.
 */
fun getIndicatorProgressColor(progress: Int): Int {
    return when (progress / 10) {
        0 -> R.color.colorIndicator_0
        1 -> R.color.colorIndicator_10
        2 -> R.color.colorIndicator_20
        3 -> R.color.colorIndicator_30
        4 -> R.color.colorIndicator_40
        5 -> R.color.colorIndicator_50
        6 -> R.color.colorIndicator_60
        7 -> R.color.colorIndicator_70
        8 -> R.color.colorIndicator_80
        9 -> R.color.colorIndicator_90
        else -> R.color.colorIndicator_100
    }
}