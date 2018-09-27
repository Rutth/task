package com.ruthb.task.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ruthb.task.R
import com.ruthb.task.adapter.TasklistAdapter
import com.ruthb.task.business.TaskBusiness
import com.ruthb.task.constants.TaskConstants
import com.ruthb.task.util.SecurityPreferences

class TaskListFragment : Fragment(), View.OnClickListener {
    private lateinit var mContext: Context
    private lateinit var mRecyclerTaskList: RecyclerView
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences

    companion object {
        fun newInstance(): TaskListFragment {
            return TaskListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        loadTask()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)

        rootView.findViewById<FloatingActionButton>(R.id.floatAddTask).setOnClickListener(this)
        mContext = rootView.context
        mTaskBusiness = TaskBusiness(mContext)
        mSecurityPreferences = SecurityPreferences(mContext)

        mRecyclerTaskList = rootView.findViewById<RecyclerView>(R.id.recyclerTasklist)

        
        mRecyclerTaskList.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = TasklistAdapter(mutableListOf())
        }


        return rootView
    }

    private fun loadTask(){
        val userId = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID).toInt()
        val taskList = mTaskBusiness.getList(userId)
        mRecyclerTaskList.adapter = TasklistAdapter(taskList)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.floatAddTask -> {
                startActivity(Intent(mContext, TaskFormActivity::class.java))
            }
        }
    }

}
