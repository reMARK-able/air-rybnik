package com.gmail.remarkable.development.airrybnik.util

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.gmail.remarkable.development.airrybnik.R

private const val NOTIFICATION_ID = 0

/**
 * Builds and sends the notification.
 */
fun NotificationManager.sendNotification(message: String, applicationContext: Context) {

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.dataUpdate_notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle("Nowe dane z GIOŚ")
        .setContentText(message)

    notify(NOTIFICATION_ID, builder.build())
}

/**
 * Sends testing notification
 */
fun sendNewDataNotification(
    appContext: Context,
    value: String
) {
    val notificationManager = ContextCompat.getSystemService(
        appContext,
        NotificationManager::class.java
    ) as NotificationManager
    notificationManager.sendNotification("PM10 wynosi: $value", appContext)
}