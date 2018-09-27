package com.ruthb.task.business

import android.content.Context
import com.ruthb.task.entities.TaskEntity
import com.ruthb.task.repository.TaskRepository

class TaskBusiness(context: Context) {

    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)

    fun getList(userId: Int): MutableList<TaskEntity> = mTaskRepository.getList(userId)


}