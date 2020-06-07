package com.foobles.kotlinnum.notification.vm

import com.foobles.kotlinnum.notification.receiver.AlarmReceiver
import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.foobles.kotlinnum.utils.ARG_ENABLE_NOTIFICATION
import com.foobles.kotlinnum.utils.cancelNotifications
import java.util.*


class NotificationViewModel(private val app: Application) : AndroidViewModel(app) {

    private val REQUEST_CODE = 0

    private val notifyPendingIntent: PendingIntent

    private val alarmManager = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private var prefs =
        app.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    private val notifyIntent = Intent(app, AlarmReceiver::class.java)

    init {
        notifyPendingIntent = PendingIntent.getBroadcast(
            getApplication(),
            REQUEST_CODE,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun setAlarm() {
        val notificationManager =
            ContextCompat.getSystemService(
                app,
                NotificationManager::class.java
            ) as NotificationManager
        notificationManager.cancelNotifications()

        val calendar: Calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, 13)

        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)


        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, notifyPendingIntent
        )
    }

    private fun cancelNotification() {
        alarmManager.cancel(notifyPendingIntent)
    }

    fun toggleNotification(isEnabled: Boolean) {
        prefs.edit().putBoolean(ARG_ENABLE_NOTIFICATION, isEnabled).apply()
        if (isEnabled) {
            setAlarm()
        } else {
            cancelNotification()
        }
    }

    fun loadNotificationStatus() =
        prefs.getBoolean(ARG_ENABLE_NOTIFICATION, false)
}