package com.ruthb.task.entities

interface OnTaskListFragmentInteractionListener {
    fun onListClick(taskId: Int)

    fun onDeleteClick(taskId: Int)

    fun onUncompleteClick(taskId: Int)

    fun onCompleteClick(taskId: Int)
}