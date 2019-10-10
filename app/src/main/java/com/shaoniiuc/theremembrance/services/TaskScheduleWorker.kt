package com.shaoniiuc.theremembrance.services

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.shaoniiuc.theremembrance.R
import com.shaoniiuc.theremembrance.activities.MainActivity

class TaskScheduleWorker(val context: Context, workParam: WorkerParameters) :
    Worker(context, workParam) {

    override fun doWork(): Result {
        val placeName = inputData.getString("place_name")
        val taskMsg = inputData.getString("task_msg")

        createChatNotification(placeName, taskMsg)

        return Result.success()
    }

    private fun createChatNotification(placeName: String?, taskMsg: String?) {
        val channelId = "25"

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(
            context, 1
            , intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager

        creatingNotificationChannel(
            notificationManager,
            channelId, context.getString(R.string.app_name),
            "Channel Description"
        )
        val pattern = longArrayOf(500, 500, 500, 500, 500, 500, 500, 500, 500)
        val mBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Your reminder: $taskMsg")
            .setContentText("You want to visit at : $placeName")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setDefaults(Notification.DEFAULT_SOUND)
            .setVibrate(pattern)
            .setAutoCancel(true)

        val notification = mBuilder.build()
        notification.flags = Notification.FLAG_AUTO_CANCEL
        notificationManager.notify(1, notification)
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun creatingNotificationChannel(
        notificationManager: NotificationManager,
        channelId: String?, channelName: String,
        channelDescription: String?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val name: CharSequence = channelName;
            val description = channelDescription
            val channel = NotificationChannel(
                channelId, name, NotificationManager
                    .IMPORTANCE_DEFAULT
            );
            channel.description = description
            // Register the channel with the system

            notificationManager.createNotificationChannel(channel)
        }
    }
}