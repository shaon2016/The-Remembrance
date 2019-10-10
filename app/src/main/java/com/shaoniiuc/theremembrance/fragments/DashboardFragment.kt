package com.shaoniiuc.theremembrance.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.shaoniiuc.theremembrance.R
import com.shaoniiuc.theremembrance.adapters.TaskRvAdapter
import com.shaoniiuc.theremembrance.helper.obtainViewModel
import com.shaoniiuc.theremembrance.models.Task
import com.shaoniiuc.theremembrance.viewmodels.DashVM
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
        adapter = TaskRvAdapter(context!!, ArrayList())
    }

    private fun initView() {
        rvTasks.layoutManager = LinearLayoutManager(context!!)
        rvTasks.adapter = adapter

        dashVM.taskLive.observe(this, Observer { tasks ->
            adapter.addUniquely(tasks as java.util.ArrayList<Task>)
        })
    }


    companion object {
        @JvmStatic
        fun newInstance() = DashboardFragment()
    }
}
