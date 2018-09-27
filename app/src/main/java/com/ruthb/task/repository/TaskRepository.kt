package com.ruthb.task.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.ruthb.task.constants.DatabaseConstants
import com.ruthb.task.entities.PriorityEntity
import com.ruthb.task.entities.TaskEntity

class TaskRepository private constructor(context: Context) {
    private var mTaskDatabaseHelper: TaskDatabaseHelper = TaskDatabaseHelper(context)

    companion object {
        fun getInstance(context: Context): TaskRepository {
            if (INSTANCE == null) {
                INSTANCE = TaskRepository(context)
            }

            return INSTANCE as TaskRepository
        }

        private var INSTANCE: TaskRepository? = null
    }

    fun getList(userId: Int): MutableList<TaskEntity> {
        val list = mutableListOf<TaskEntity>()

        try {
            val cursor: Cursor
            val db = mTaskDatabaseHelper.readableDatabase

            cursor = db.rawQuery("SELECT * FROM ${DatabaseConstants.TASK.TABLE_NAME}" +
                    " WHERE ${DatabaseConstants.TASK.COLUMNS.USERID} = $userId", null)

            if (cursor.count > 0) {
                //cursor.moveToFirst()
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TASK.COLUMNS.ID))
                    val priorityId = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TASK.COLUMNS.PRIORITYID))
                    val description = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TASK.COLUMNS.DESCRIPTION))
                    val dueDate = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TASK.COLUMNS.DUEDATE))
                    val complete = (cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TASK.COLUMNS.COMPLETE)) == 1)



                    list.add(TaskEntity(id, userId, priorityId, description, dueDate, complete))
                }
            }
            cursor.close()

        } catch (e: Exception) {
            return list
        }

        return list
    }

    fun get(id: Int): TaskEntity? {
        var taskEntity: TaskEntity? = null

        try {
            val cursor: Cursor
            val db = mTaskDatabaseHelper.readableDatabase

            val projection = arrayOf(DatabaseConstants.TASK.COLUMNS.ID,
                    DatabaseConstants.TASK.COLUMNS.USERID,
                    DatabaseConstants.TASK.COLUMNS.PRIORITYID,
                    DatabaseConstants.TASK.COLUMNS.DESCRIPTION,
                    DatabaseConstants.TASK.COLUMNS.DUEDATE,
                    DatabaseConstants.TASK.COLUMNS.COMPLETE)

            val selection = "${DatabaseConstants.TASK.COLUMNS.ID} = ?"
            val selectionArgs = arrayOf(id.toString())

            cursor = db.query(DatabaseConstants.TASK.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
            if (cursor.count > 0) {
                cursor.moveToFirst()

                val id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TASK.COLUMNS.ID))
                val userId = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TASK.COLUMNS.USERID))
                val priorityId = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TASK.COLUMNS.PRIORITYID))
                val description = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TASK.COLUMNS.DESCRIPTION))
                val dueDate = cursor.getString(cursor.getColumnIndex(DatabaseConstants.TASK.COLUMNS.DUEDATE))
                val complete = (cursor.getInt(cursor.getColumnIndex(DatabaseConstants.TASK.COLUMNS.COMPLETE)) == 1)

                taskEntity = TaskEntity(id, userId, priorityId, description, dueDate, complete)
            }
            cursor.close()

        } catch (e: Exception) {
            return taskEntity
        }

        return taskEntity

    }

    fun insert(task: TaskEntity) {
        try{
            val db = mTaskDatabaseHelper.writableDatabase

            val complete: Int = if (task.complete) 1 else 0


            val insertValues = ContentValues()
            insertValues.put(DatabaseConstants.TASK.COLUMNS.USERID, task.userId)
            insertValues.put(DatabaseConstants.TASK.COLUMNS.PRIORITYID, task.priorityId)
            insertValues.put(DatabaseConstants.TASK.COLUMNS.DESCRIPTION, task.description)
            insertValues.put(DatabaseConstants.TASK.COLUMNS.DUEDATE, task.dueDate)
            insertValues.put(DatabaseConstants.TASK.COLUMNS.COMPLETE, complete)


            db.insert(DatabaseConstants.TASK.TABLE_NAME, null, insertValues)
        } catch (e: Exception){
            throw e
        }
    }

    fun update(task: TaskEntity) {
        try{
            val db = mTaskDatabaseHelper.writableDatabase

            val complete: Int = if (task.complete) 1 else 0


            val updateValues = ContentValues()
            updateValues.put(DatabaseConstants.TASK.COLUMNS.USERID, task.userId)
            updateValues.put(DatabaseConstants.TASK.COLUMNS.PRIORITYID, task.priorityId)
            updateValues.put(DatabaseConstants.TASK.COLUMNS.DESCRIPTION, task.description)
            updateValues.put(DatabaseConstants.TASK.COLUMNS.DUEDATE, task.dueDate)
            updateValues.put(DatabaseConstants.TASK.COLUMNS.COMPLETE, complete)


            val selection = "${DatabaseConstants.TASK.COLUMNS.ID} = ?"
            val selectionArgs = arrayOf(task.id.toString())

            db.update(DatabaseConstants.TASK.TABLE_NAME, updateValues, selection, selectionArgs)
        } catch (e: Exception){
            throw e
        }

    }

    fun delete(id: Int){
       try{
           val db = mTaskDatabaseHelper.writableDatabase

           val where = "${DatabaseConstants.TASK.COLUMNS.ID} = ?"
           val whereArgs = arrayOf(id.toString())

           db.delete(DatabaseConstants.TASK.TABLE_NAME, where, whereArgs)
       } catch (e: Exception){
           throw e
       }
    }
}