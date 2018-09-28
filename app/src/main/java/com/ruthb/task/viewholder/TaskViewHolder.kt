package com.ruthb.task.viewholder

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ruthb.task.R
import com.ruthb.task.entities.OnTaskListFragmentInteractionListener
import com.ruthb.task.entities.TaskEntity
import com.ruthb.task.repository.PriorityCacheConstants

class TaskViewHolder(itemView: View, val context: Context, val listener: OnTaskListFragmentInteractionListener): RecyclerView.ViewHolder(itemView) {
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

        mTextDescription.setOnClickListener {
            listener.onListClick(task.id)
        }
        mTextDescription.setOnLongClickListener{
            showConfirmationDialog(task)
            true
        }

        mImgTask.setOnClickListener{
            if(task.complete){
                listener.onUncompleteClick(task.id)
            } else{
                listener.onCompleteClick(task.id)
            }
        }
    }

    private fun showConfirmationDialog(task: TaskEntity) {
//        listener.onDeleteClick(task.id)
        AlertDialog.Builder(context)
                .setTitle("Remoção de tarefa")
                .setMessage("Deseja remover ${task.description}?")
                .setIcon(R.drawable.ic_delete_black_24dp)
                .setPositiveButton("Remover", handleRemov(listener, task.id))
                .setNegativeButton("Cancelar", null).show()

        //{ dialog: DialogInterface, which: Int -> listener.onDeleteClick((task.id))  }
    }

    private class handleRemov(val listener: OnTaskListFragmentInteractionListener, val taskId: Int): DialogInterface.OnClickListener{
        override fun onClick(dialog: DialogInterface?, which: Int) {
            listener.onDeleteClick(taskId)
        }

    }
}