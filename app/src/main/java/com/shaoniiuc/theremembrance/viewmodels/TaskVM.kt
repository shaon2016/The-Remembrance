package com.shaoniiuc.theremembrance.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.google.android.libraries.places.api.model.Place
import com.shaoniiuc.theremembrance.data.AppDb
import com.shaoniiuc.theremembrance.helper.Util
import com.shaoniiuc.theremembrance.models.Task
import com.shaoniiuc.theremembrance.services.TaskScheduleWorker
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

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

    var formattedDate = MutableLiveData<String>()
    var formattedTime = MutableLiveData<String>()

    var isSaveSuccessful = MutableLiveData<Boolean>()

    private val toastLive_ = MutableLiveData<String>()
    val toastLive: LiveData<String> = toastLive_

    init {
        val calendar = Calendar.getInstance()
        year.value = calendar.get(Calendar.YEAR)
        month.value = calendar.get(Calendar.MONTH)
        day.value = calendar.get(Calendar.DAY_OF_MONTH)
        hour.value = calendar.get(Calendar.HOUR_OF_DAY)
        min.value = calendar.get(Calendar.MINUTE)

        setFormattedDateAndTime()
    }

    fun insert() {
        if (isValidate()) {
            initTask()
            scheduleReminder()
            insertTask()
        } else toastLive_.value = "Insert Task Message"
    }

    @SuppressLint("CheckResult")
    private fun insertTask() {
        Observable.fromCallable {
            db.taskDao().insert(taskLive.value!!)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                toastLive_.value = "Reminder Successfully Posted"
                isSaveSuccessful.value = true
            }
    }

    private fun scheduleReminder() {
        val task = taskLive.value!!
        //Setting Schedule before 24 hours
        val _24HrBack = Util._24HrBack(getTaskTimeInMillis())
        //This checking is required, to avoid immidiate fire of the work
        if (_24HrBack > System.currentTimeMillis())
             createRequest(_24HrBack)
        //Setting Schedule for exact time
        if (getTaskTimeInMillis() > System.currentTimeMillis())
             createRequest(getTaskTimeInMillis())

        taskLive.value = task

        //updateDb()
    }

    private fun createRequest(initialDelay: Long) {
        val wManager = WorkManager.getInstance(getApplication())
        val dataBuilder = Data.Builder()
        val task = taskLive.value!!
        dataBuilder.putString("place_name", task.placeName)
        dataBuilder.putString("task_msg", task.taskMsg)
        val data = dataBuilder.build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val diff = initialDelay - System.currentTimeMillis()
        val request = OneTimeWorkRequest.Builder(TaskScheduleWorker::class.java)
            .setInputData(data)
            .addTag(task.placeWeb)
            .setConstraints(constraints)
            .setInitialDelay(diff, TimeUnit.MILLISECONDS)
            .build()

        wManager.enqueue(request)
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
        formattedDate.value = Util.formatDate(getTaskTimeInMillis())
        formattedTime.value = Util.formatTime(getTaskTimeInMillis())

    }

    private fun getTaskTimeInMillis(): Long {
        val calendar = GregorianCalendar(
            year.value!!, month.value!!, day.value!!, hour.value!!,
            min.value!!
        )

        return calendar.timeInMillis
    }

    private fun initTask() {
        val task = Task()
        task.taskMsg = taskMsgLive.value!!
        task.date = getTaskTimeInMillis()
        task.time = getTaskTimeInMillis()
        val place = placeLive.value
        task.placeName = place?.name ?: "Not Available"
        task.placeAddress = place?.address ?: "Not Available"
        task.placeMobile = place?.phoneNumber ?: "Not Available"
        task.placeRating = String.format("${place?.rating ?: "Not Available"}")
        task.placeWeb = String.format("${place?.websiteUri ?: "Not Available"}")
        task.lat = place?.latLng?.latitude ?: 0.0
        task.lon = place?.latLng?.longitude ?: 0.0

        taskLive.value = task
    }
}