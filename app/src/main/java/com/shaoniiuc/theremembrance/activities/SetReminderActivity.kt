package com.shaoniiuc.theremembrance.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TimePicker
import androidx.core.content.ContextCompat
import com.google.android.libraries.places.api.model.Place
import com.shaoniiuc.theremembrance.R
import com.shaoniiuc.theremembrance.helper.Util
import com.shaoniiuc.theremembrance.helper.obtainViewModel
import com.shaoniiuc.theremembrance.viewmodels.TaskVM
import kotlinx.android.synthetic.main.activity_set_reminder.*
import kotlinx.android.synthetic.main.my_toolbar.*

class SetReminderActivity : AppCompatActivity() {
    private lateinit var place: Place
    private lateinit var taskVM: TaskVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_reminder)


        initVar()
        initView()
    }

    private fun initVar() {
        place = intent?.extras?.getParcelable<Place>("place") as Place
        taskVM = obtainViewModel(TaskVM::class.java)
    }

    private fun initView() {
        taskVM.formattedTime.observe(this, androidx.lifecycle.Observer {
            btnTime.text = it
        })
        taskVM.formattedDate.observe(this, androidx.lifecycle.Observer {
            btnDate.text = it
        })
        taskVM.toastLive.observe(this, androidx.lifecycle.Observer {msg->
            Util.showShortToast(this, msg)
        })
        taskVM.isSaveSuccessful.observe(this, androidx.lifecycle.Observer {
            setResult(Activity.RESULT_OK)
            finish()
        })

        handleBtn()
        configureToolbar()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar?.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_white_24dp)
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.title = getString(R.string.set_reminder)
        toolbar?.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        toolbar?.setNavigationOnClickListener { v -> onBackPressed() }
    }


    private fun handleBtn() {
        btnDate.setOnClickListener {
            pickDate()
        }

        btnTime.setOnClickListener {
            pickTime()
        }

        btnDone.setOnClickListener {
            taskVM.setPlace(place)
            taskVM.setTaskMsg(evTaskMsg.text.toString())
            taskVM.insert()
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun pickTime() {
        TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { picker, h, m ->
                taskVM.setTime(h, m)
            }, taskVM.hour.value!!,
            taskVM.min.value!!, true
        ).show()
    }

    private fun pickDate() {
        DatePickerDialog.OnDateSetListener { picker, y, m, d ->
            taskVM.setDate(y, m, d)
        }

        DatePickerDialog(
            this, DatePickerDialog.OnDateSetListener { picker, y, m, d ->
                taskVM.setDate(y, m, d)
            }, taskVM.year.value!!,
            taskVM.month.value!!, taskVM.day.value!!
        ).show()
    }

}
