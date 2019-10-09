package com.shaoniiuc.theremembrance.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shaoniiuc.theremembrance.models.Task

@Dao
interface TaskDao {

    @Query("select * from task")
    fun all() : LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)
}