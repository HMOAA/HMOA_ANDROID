package com.hmoa.app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmAppService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("FCM TEST", "message : ${remoteMessage}")
        if(remoteMessage.notification != null){
            sendNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
        } else if (remoteMessage.data.isNotEmpty()) {
            sendTopNotification(remoteMessage.data)
        }
    }
    private fun sendNotification(title : String?, body : String?){
        val uniId = (System.currentTimeMillis() / 7).toInt()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)

        val channelId = "hmoa_channel"

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_SOUND)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
        channel.apply{
            setShowBadge(false)
        }
        notificationManager.notify(uniId, notificationBuilder.build())
    }
    private fun sendTopNotification(fcmData : Map<String, String>){
        val CHANNEL_DEFAULT_IMPORTANCE = "channelId"
        val ONGOING_NOTIFICATION = 1

        val notificationIntent = Intent(this, MainActivity::class.java).apply{
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra("DEEPLINK",fcmData["deeplink"])
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)

        val notification = Notification.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
            .setContentTitle(fcmData["title"])
            .setContentText(fcmData["content"])
            .setSmallIcon(com.hmoa.core_designsystem.R.drawable.ic_app_default_1)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(CHANNEL_DEFAULT_IMPORTANCE, "HMOA Channel", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(ONGOING_NOTIFICATION, notification)
    }
}