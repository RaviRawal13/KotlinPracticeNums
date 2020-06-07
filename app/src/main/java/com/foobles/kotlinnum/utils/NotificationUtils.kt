package com.foobles.kotlinnum.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.foobles.kotlinnum.R
import com.foobles.kotlinnum.nav.NavigationActivity

// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0


fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    // Create the content intent for the notification, which launches
    // this activity
    val contentIntent = Intent(applicationContext, NavigationActivity::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val learningBitmap = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.bodmas
    )
    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(learningBitmap)
        .bigLargeIcon(null)

    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.practice_notification_channel_id)
    )

        .setSmallIcon(R.drawable.ic_learning_invert)
        .setContentTitle(applicationContext
            .getString(R.string.app_name))
        .setContentText(messageBody)

        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)

        .setStyle(bigPicStyle)
        .setLargeIcon(learningBitmap)

        .setPriority(NotificationCompat.PRIORITY_HIGH)
    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}
