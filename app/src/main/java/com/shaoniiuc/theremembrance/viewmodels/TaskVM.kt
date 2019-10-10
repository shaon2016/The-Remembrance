package com.shaoniiuc.theremembrance.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.libraries.places.api.model.Place
import com.shaoniiuc.theremembrance.data.AppDb
import com.shaoniiuc.theremembrance.helper.Util
import com.shaoniiuc.theremembrance.models.Task
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

    var formattedDate  = MutableLiveData<String>()
    var formattedTime  = MutableLiveData<String>()

    var isSaveSuccessful  = MutableLiveData<Boolean>()

    private val toastLive_ = MutableLiveData<String>()
    val toastLive :LiveData<String> = toastLive_

    init {
        val calendar = Calendar.getInstance()
        year.value = calendar.get(Calendar.YEAR)
        month.value = calendar.get(Calendar.MONTH)
        day.value = calendar.get(Calendar.DAY_OF_MONTH)
        hour.value = calendar.get(Calendar.HOUR)
        min.value = calendar.get(Calendar.MINUTE)

        setFormattedDateAndTime()
    }

    @SuppressLint("CheckResult")
    fun insert() {
        if (isValidate()) {
            initTask()
            Observable.fromCallable {
                db.taskDao().insert(taskLive.value!!)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    toastLive_.value = "Reminder Successfully Posted"
                    isSaveSuccessful.value = true
                }
        }

        else toastLive_.value = "Insert Task Message"
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

        setFormattedDateAndTime()
    }

    fun setDate(y: Int, m: Int, d: Int) {
        year.value = y
        month.value = m
        day.value = d

        setFormattedDateAndTime()
    }

    private fun setFormattedDateAndTime() {
        getTimeInMillis()

        formattedDate.value = Util.formatDate(getTimeInMillis())
        formattedTime.value = Util.formatTime(getTimeInMillis())


    }

    private fun getTimeInMillis(): Long {
        val calendar = GregorianCalendar(year.value!!, month.value!!, day.value!!, hour.value!!,
            min.value!!)

        return calendar.timeInMillis
    }

    private fun initTask() {
        val task = Task()
        task.taskMsg = taskMsgLive.value!!
        task.date = getTimeInMillis()
        task.time = getTimeInMillis()
        val place = placeLive.value
        task.placeName = place?.name ?: "Not Available"
        task.placeAddress = place?.address ?: "Not Available"
        task.placeMobile = place?.phoneNumber ?: "Not Available"
        task.placeRating = String.format("${place?.rating ?: "Not Available"}")
        task.placeWeb = String.format("${place?.websiteUri ?: "Not Available"}")

        taskLive.value = task
    }
}