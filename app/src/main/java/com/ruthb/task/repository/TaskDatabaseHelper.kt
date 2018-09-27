package com.ruthb.task.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ruthb.task.constants.DatabaseConstants

class TaskDatabaseHelper(context: Context) :SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION: Int = 1
        private val DATABASE_NAME: String = "task.db"
    }


    private val createTableUser = """ CREATE TABLE ${DatabaseConstants.USER.TABLE_NAME}(
        ${DatabaseConstants.USER.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${DatabaseConstants.USER.COLUMNS.NAME} TEXT,
        ${DatabaseConstants.USER.COLUMNS.EMAIL} TEXT,
        ${DatabaseConstants.USER.COLUMNS.PASSWORD} TEXT

    );"""

    private val createTablePriority = """ CREATE TABLE ${DatabaseConstants.PRIORITY.TABLE_NAME}(
        ${DatabaseConstants.PRIORITY.COLUMNS.ID} INTEGER PRIMARY KEY,
        ${DatabaseConstants.PRIORITY.COLUMNS.DESCRIPTION} TEXT

    );"""

    private val createTableTask = """ CREATE TABLE ${DatabaseConstants.TASK.TABLE_NAME}(
        ${DatabaseConstants.TASK.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${DatabaseConstants.TASK.COLUMNS.PRIORITYID} INTEGER,
        ${DatabaseConstants.TASK.COLUMNS.USERID} INTEGER,
        ${DatabaseConstants.TASK.COLUMNS.DESCRIPTION} TEXT,
        ${DatabaseConstants.TASK.COLUMNS.COMPLETE} INTEGER,
        ${DatabaseConstants.TASK.COLUMNS.DUEDATE} TEXT

    );"""

    private val insertPriorities = """INSERT INTO ${DatabaseConstants.PRIORITY.TABLE_NAME}
        VALUES (1, 'Baixa'),(2, 'Média'),(3, 'Alta'),(4, 'Crítica');
        """

    private val deleteTableUser = "drop table if exists ${DatabaseConstants.USER.TABLE_NAME}"
    private val deleteTablePriority = "drop table if exists ${DatabaseConstants.PRIORITY.TABLE_NAME}"
    private val deleteTableTask = "drop table if exists ${DatabaseConstants.TASK.TABLE_NAME}"



    override fun onCreate(sqlLite: SQLiteDatabase) {
        sqlLite.execSQL(createTableUser)
        sqlLite.execSQL(createTablePriority)
        sqlLite.execSQL(createTableTask)

        sqlLite.execSQL(insertPriorities)
    }

    override fun onUpgrade(sqlLite: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        sqlLite.execSQL(deleteTableUser)
        sqlLite.execSQL(deleteTablePriority)
        sqlLite.execSQL(deleteTableTask)

        sqlLite.execSQL(createTableUser)
        sqlLite.execSQL(createTablePriority)
        sqlLite.execSQL(createTableTask)



    }


}