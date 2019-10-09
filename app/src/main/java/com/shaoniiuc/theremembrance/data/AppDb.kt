package com.shaoniiuc.theremembrance.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shaoniiuc.theremembrance.data.dao.TaskDao
import com.shaoniiuc.theremembrance.models.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private var instance: AppDb? = null

        fun getInstance(context: Context) = if (instance == null) create(context) else instance!!

        private fun create(context: Context) = Room.databaseBuilder(
            context,
            AppDb::class.java, "food"
        )
            .allowMainThreadQueries()
            .build()
    }
}