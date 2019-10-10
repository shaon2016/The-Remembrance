package com.shaoniiuc.theremembrance.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.shaoniiuc.theremembrance.R
import com.shaoniiuc.theremembrance.activities.RemindersMapActivity
import com.shaoniiuc.theremembrance.adapters.TaskRvAdapter
import com.shaoniiuc.theremembrance.helper.obtainViewModel
import com.shaoniiuc.theremembrance.models.Task
import com.shaoniiuc.theremembrance.viewmodels.DashVM
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*


class DashboardFragment : Fragment() {
    private lateinit var adapter: TaskRvAdapter
    private lateinit var dashVM: DashVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVar()
        initView()
    }

    private fun initVar() {
        dashVM = obtainViewModel(DashVM::class.java)
        adapter = TaskRvAdapter(context!!, ArrayList(), dashVM)
    }

    private fun initView() {
        rvTasks.layoutManager = LinearLayoutManager(context!!)
        rvTasks.adapter = adapter

        dashVM.taskLive.observe(this, Observer { tasks ->
            if (!tasks.isNullOrEmpty())
                adapter.addUniquely(tasks as java.util.ArrayList<Task>)
            else tvNoTaskMsg.visibility = View.VISIBLE
        })
    }




    companion object {
        @JvmStatic
        fun newInstance() = DashboardFragment()
    }
}
