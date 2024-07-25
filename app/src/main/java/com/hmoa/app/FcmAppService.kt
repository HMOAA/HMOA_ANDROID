package com.hmoa.app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmAppService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if(remoteMessage.data.isNotEmpty()){
            sendNotification(remoteMessage.data)
        }
    }
    private fun sendNotification(fcmData : Map<String, String>){
        val CHANNEL_DEFAULT_IMPORTANCE = "channelId"
        val ONGOING_NOTIFICATION = 1

        val notificationIntent = Intent(this, MainActivity::class.java).apply{
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra("deeplink",fcmData["deeplink"])
            putExtra("id",fcmData["id"])
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = Notification.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
            .setContentTitle(fcmData["title"])
            .setContentText(fcmData["content"])
            .setSmallIcon(com.hmoa.core_designsystem.R.drawable.ic_app_default_1)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(CHANNEL_DEFAULT_IMPORTANCE, "HMOA Channel", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(ONGOING_NOTIFICATION, notification)
    }
}