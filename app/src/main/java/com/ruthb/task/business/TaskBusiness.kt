package com.ruthb.task.business

import android.content.Context
import com.ruthb.task.constants.TaskConstants
import com.ruthb.task.entities.TaskEntity
import com.ruthb.task.repository.TaskRepository
import com.ruthb.task.util.SecurityPreferences

class TaskBusiness(context: Context) {

    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun getList(taskFilter: Int): MutableList<TaskEntity> {
        val userId = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID).toInt()
        return mTaskRepository.getList(userId, taskFilter)
    }

    fun insert(task: TaskEntity) {
        try {
            mTaskRepository.insert(task)
        } catch (e: Exception) {
            throw e
        }
    }

    fun get(id: Int) = mTaskRepository.get(id)

    fun update(task: TaskEntity) {
        try {
            mTaskRepository.update(task)
        } catch (e: Exception) {
            throw e
        }
    }

}