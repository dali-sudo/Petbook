package com.example.petbook.views

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.util.Base64
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.petbook.R
import com.example.petbook.viewModel.PostViewModel
import okhttp3.internal.notify
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.example.petbook.model.*
import com.example.petbook.repository.NotificationRepository
import com.example.petbook.repository.PostRepository
import com.example.petbook.repository.SessionManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BackgroundService : Service() {
    lateinit var list: MutableList<NotificationResponse>
    val Repo = NotificationRepository()


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel"
            val descriptionText = "test"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

      getNotifications(this,"0")
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    fun getNotifications(context: Context,count:String) {
        if (SessionManager.getString(context,"id") !=null ) {
        GlobalScope.launch {
            try {

                    val notificationRequest = NotificationRequest(
                        id =SessionManager.getString(context,"id"),
                        count

                    )

                    val response = Repo.getAll(notificationRequest)

                if (response?.code() == 200) {
                    list= response.body()!!
if(list.size>0) {
    for(i in 0..list.size-1){
        val imageBytes = Base64.decode(list[i].sender!!.avatar, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    var builder = NotificationCompat.Builder(context, "notif")
        .setSmallIcon(R.drawable.dogicon)
        .setContentTitle(list[i].title)
        .setContentText(list[i].content)
        .setLargeIcon(decodedImage)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(context)) {
        // notificationId is a unique int for each notification that you must define
        notify(i, builder.build())}
    }
}
         getNotifications(context,"0")       } else {

                }

            } catch (ex: Exception) {

                println(BaseResponse.Error(ex.message ))
            }
        }
    //ex.message
    }
    }

}