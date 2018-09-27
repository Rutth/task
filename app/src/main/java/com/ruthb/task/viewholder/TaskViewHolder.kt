package com.ruthb.task.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ruthb.task.R
import com.ruthb.task.entities.TaskEntity
import com.ruthb.task.repository.PriorityCacheConstants

class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val mTextDescription: TextView = itemView.findViewById(R.id.tvDescription)
    private val mTextPriority: TextView = itemView.findViewById(R.id.tvPriority)
    private val mTextDate: TextView = itemView.findViewById(R.id.tvDueDate)
    private val mImgTask: ImageView = itemView.findViewById(R.id.imgTask)

    fun bindData(task: TaskEntity){
        mTextDescription.text = task.description
        mTextPriority.text = PriorityCacheConstants.getPriorityDescription(task.priorityId)
        mTextDate.text = ""

        if(task.complete){
            mImgTask.setImageResource(R.drawable.ic_done)
        }
    }
}