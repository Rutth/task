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
import com.ruthb.task.entities.OnTaskListFragmentInteractionListener
import com.ruthb.task.util.SecurityPreferences

class TaskListFragment : Fragment(), View.OnClickListener {
    private lateinit var mContext: Context
    private lateinit var mRecyclerTaskList: RecyclerView
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mListener: OnTaskListFragmentInteractionListener

    private var mTaskFilter: Int = 0

    companion object {
        fun newInstance(taskFilter: Int): TaskListFragment {
            val args: Bundle = Bundle()
            args.putInt(TaskConstants.TASKFILTER.KEY, taskFilter)

            val fragment = TaskListFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null){
            mTaskFilter = arguments!!.getInt(TaskConstants.TASKFILTER.KEY)
        }

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
        mListener = object: OnTaskListFragmentInteractionListener{
            override fun onListClick(taskId: Int) {

                val bundle: Bundle = Bundle()
                bundle.putInt(TaskConstants.BUNDLE.TASKID, taskId)
                startActivity(Intent(mContext, TaskFormActivity::class.java).putExtras(bundle))
            }

        }

        mRecyclerTaskList = rootView.findViewById<RecyclerView>(R.id.recyclerTasklist)


        mRecyclerTaskList.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = TasklistAdapter(mutableListOf(), mListener)
        }


        return rootView
    }

    private fun loadTask(){
        mRecyclerTaskList.adapter = TasklistAdapter(mTaskBusiness.getList(mTaskFilter), mListener)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.floatAddTask -> {
                startActivity(Intent(mContext, TaskFormActivity::class.java))
            }
        }
    }

}
