package com.ruthb.task.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.ruthb.task.constants.DatabaseConstants
import com.ruthb.task.entities.UserEntity

class UserRepository private constructor(context: Context){

    private var mTaskDatabaseHelper: TaskDatabaseHelper = TaskDatabaseHelper(context)

    companion object {
        fun getInstance(context: Context): UserRepository{
            if(INSTANCE == null){
                INSTANCE = UserRepository(context)
            }

            return INSTANCE as UserRepository
        }

        private var INSTANCE: UserRepository? = null

    }

    fun get(email: String, password: String): UserEntity?{
        var userEntity: UserEntity? = null

        try{
            val cursor: Cursor
            val db = mTaskDatabaseHelper.readableDatabase

            val projection = arrayOf(DatabaseConstants.USER.COLUMNS.ID,
                    DatabaseConstants.USER.COLUMNS.NAME,
                    DatabaseConstants.USER.COLUMNS.EMAIL,
                    DatabaseConstants.USER.COLUMNS.PASSWORD)

            val selection = "${DatabaseConstants.USER.COLUMNS.EMAIL} = ? AND ${DatabaseConstants.USER.COLUMNS.PASSWORD} = ?"
            val selectionArgs = arrayOf(email, password)

            cursor = db.query(DatabaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
            if(cursor.count > 0){
                cursor.moveToFirst()

                val userId = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.USER.COLUMNS.ID))
                val name = cursor.getString(cursor.getColumnIndex(DatabaseConstants.USER.COLUMNS.NAME))
                val email = cursor.getString(cursor.getColumnIndex(DatabaseConstants.USER.COLUMNS.EMAIL))

                userEntity = UserEntity(userId, name, email)
            }
            cursor.close()

        } catch (e: Exception){
            return userEntity
        }

        return userEntity

    }

    fun insert(name: String, email: String, password: String): Int{
        val db = mTaskDatabaseHelper.writableDatabase

        val insertValues = ContentValues()
        insertValues.put(DatabaseConstants.USER.COLUMNS.NAME, name)
        insertValues.put(DatabaseConstants.USER.COLUMNS.EMAIL, email)
        insertValues.put(DatabaseConstants.USER.COLUMNS.PASSWORD, password)

        return db.insert(DatabaseConstants.USER.TABLE_NAME, null, insertValues).toInt()
    }

    fun isEmailExistent(email: String): Boolean{
        var ret: Boolean //retorna false

        try {

            val cursor: Cursor
            val db = mTaskDatabaseHelper.readableDatabase

            val projection = arrayOf(DatabaseConstants.USER.COLUMNS.ID)

            val selection = "${DatabaseConstants.USER.COLUMNS.EMAIL} = ?"
            val selectionArgs = arrayOf(email)

            cursor = db.query(DatabaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
            ret = cursor.count > 0

            cursor.close()

        } catch (e: Exception){
            throw e
        }

        return ret
        //db.rawQuery("SELECT * FROM user WHERE email = ${email}", null)

    }

}