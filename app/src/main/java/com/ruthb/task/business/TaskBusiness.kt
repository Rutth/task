package com.ruthb.task.business

import android.content.Context
import com.ruthb.task.entities.TaskEntity
import com.ruthb.task.repository.TaskRepository

class TaskBusiness(context: Context) {

    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)

    fun getList(userId: Int): MutableList<TaskEntity> = mTaskRepository.getList(userId)

    fun insert(task: TaskEntity) {
        try {
            mTaskRepository.insert(task)
        } catch (e: Exception) {
            throw e
        }
    }
}