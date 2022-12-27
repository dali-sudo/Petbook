package com.example.petbook.viewModel

import android.app.Application
import android.app.Notification
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.petbook.R
import com.example.petbook.model.BaseResponse
import com.example.petbook.model.NotificationRequest
import com.example.petbook.model.NotificationResponse
import com.example.petbook.repository.NotificationRepository
import com.example.petbook.repository.SessionManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NotificationViewModel (application: Application) : AndroidViewModel(application) {
   val list: MutableLiveData<MutableList<NotificationResponse>> = MutableLiveData()
    val Repo = NotificationRepository()
    fun getNotifications(id:String) {

            viewModelScope.launch {
                try {

                    val notificationRequest = NotificationRequest(
                        id = id

                    )

                    val response = Repo.getAllNotification(notificationRequest)

                    if (response?.code() == 200) {
                        list.value= response.body()
                            }

                     else {
                    }

                } catch (ex: Exception) {

                    println(BaseResponse.Error(ex.message ))
                }
           }} }
            //ex.message

