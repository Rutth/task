package com.ruthb.task.entities

data class TaskEntity(val id: Int,
                      val userId: Int,
                      val priorityId: Int,
                      var description: String,
                      var dueDate: String,
                      var complete: Boolean)