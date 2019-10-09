package com.shaoniiuc.theremembrance.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.libraries.places.api.model.Place
import com.shaoniiuc.theremembrance.data.AppDb
import com.shaoniiuc.theremembrance.models.Task
import java.util.*

class TaskVM(application: Application) : AndroidViewModel(application) {
    private val db = AppDb.getInstance(application.applicationContext)

    private var taskLive = MutableLiveData<Task>()
    private var taskMsgLive = MutableLiveData<String>()
    private var placeLive = MutableLiveData<Place>()
     var year = MutableLiveData<Int>()
     var month = MutableLiveData<Int>()
     var day = MutableLiveData<Int>()
     var hour = MutableLiveData<Int>()
     var min = MutableLiveData<Int>()

    init {
        val calendar = Calendar.getInstance()
        year.value = calendar.get(Calendar.YEAR)
        month.value = calendar.get(Calendar.MONTH)
        day.value = calendar.get(Calendar.DAY_OF_MONTH)
        hour.value = calendar.get(Calendar.HOUR)
        min.value = calendar.get(Calendar.MINUTE)


    }

    fun insert() {
        if (isValidate())
            Thread {
                db.taskDao().insert(taskLive.value!!)
            }.start()
    }

    private fun isValidate() = !taskMsgLive.value.isNullOrEmpty() && placeLive.value != null

    fun setPlace(place: Place) {
        placeLive.value = place
    }

    fun setTaskMsg(taskMsg: String) {
        taskMsgLive.value = taskMsg
    }

    fun setTime(h: Int, m: Int) {
        hour.value = h
        min.value = m
    }

    fun setDate(y: Int, m: Int, d: Int) {
        year.value = y
        month.value = m
        day.value = d
    }
}