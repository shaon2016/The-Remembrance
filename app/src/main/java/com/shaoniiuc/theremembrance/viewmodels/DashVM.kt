package com.shaoniiuc.theremembrance.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.work.WorkManager
import com.shaoniiuc.theremembrance.data.AppDb
import com.shaoniiuc.theremembrance.models.Task

class DashVM(application: Application) : AndroidViewModel(application) {

    private val taskDao = AppDb.getInstance(application).taskDao()

    var taskLive = taskDao.all()

    fun delete(task: Task) {
        Thread {
            WorkManager.getInstance(getApplication()).cancelAllWorkByTag(task.placeWeb)
            taskDao.delete(task)
        }.start()
    }
}