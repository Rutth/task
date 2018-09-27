package com.ruthb.task.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ruthb.task.R
import com.ruthb.task.entities.TaskEntity
import com.ruthb.task.viewholder.TaskViewHolder

class TasklistAdapter(val taskList: List<TaskEntity>) : RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val context = parent?.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_task_list, parent, false)

        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.count()
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]

        holder.bindData(task)
    }
}