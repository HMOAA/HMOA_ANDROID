package com.hmoa.app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmAppService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        val pref = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("token", token).apply()
        editor.commit()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if(remoteMessage.notification != null){
            sendNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
        } else if (remoteMessage.data.isNotEmpty()) {
            sendTopNotification(remoteMessage.data["title"].toString(), remoteMessage.data["body"].toString())
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
    private fun sendTopNotification(title : String?, body : String?){
        val CHANNEL_DEFAULT_IMPORTANCE = "channelId"
        val ONGOING_NOTIFICATION = 1

        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)

        val notification = Notification.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(com.hmoa.core_designsystem.R.drawable.ic_app_default_1)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(CHANNEL_DEFAULT_IMPORTANCE, "HMOA Channel", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(ONGOING_NOTIFICATION, notification)
    }
}