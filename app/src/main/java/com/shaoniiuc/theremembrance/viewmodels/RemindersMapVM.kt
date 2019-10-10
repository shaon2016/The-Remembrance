package com.shaoniiuc.theremembrance.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.shaoniiuc.theremembrance.data.AppDb

class RemindersMapVM(application: Application) : AndroidViewModel(application) {

    private val taskDao = AppDb.getInstance(application.applicationContext).taskDao()

    val tasksLive = taskDao.all()
}