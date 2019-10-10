package com.shaoniiuc.theremembrance.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shaoniiuc.theremembrance.R
import com.shaoniiuc.theremembrance.helper.Util
import com.shaoniiuc.theremembrance.models.Task

class TaskRvAdapter(val context: Context, val items: ArrayList<Task>) :
    RecyclerView.Adapter<TaskRvAdapter.MyReminderVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReminderVH {
        return MyReminderVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_task_row,
                parent, false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MyReminderVH, position: Int) {
        holder.bind(getItem(position))
    }

    fun getItem(position: Int) = items[position]

    fun addUniquely(tasks: java.util.ArrayList<Task>) {
        val itemList = items
        for (t in tasks) {
            val id = t.id
            val exists = itemList.any { it.id == id }
            if (!exists)
                add(t)
        }
    }

   private fun add(t : Task) {
        items.add(t)
        notifyItemInserted(itemCount - 1)
    }

    inner class MyReminderVH(v: View) : RecyclerView.ViewHolder(v) {
        private val tvTaskMsg = v.findViewById<TextView>(R.id.tvTaskMsg)
        private val tvTime = v.findViewById<TextView>(R.id.tvTime)
        private val tvDate = v.findViewById<TextView>(R.id.tvDate)
        private val ivEdit = v.findViewById<ImageView>(R.id.ivEdit)
        private val ivDelete = v.findViewById<ImageView>(R.id.ivDelete)

        fun bind(t: Task) {
            tvTaskMsg.text = t.taskMsg
            tvTime.text = Util.formatTime(t.time)
            tvDate.text = Util.formatDate(t.date)

            ivEdit.setOnClickListener {

            }
            ivDelete.setOnClickListener {

            }
        }

    }
}