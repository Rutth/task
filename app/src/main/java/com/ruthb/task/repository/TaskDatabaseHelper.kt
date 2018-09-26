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
        ${DatabaseConstants.USER.COLUMNS.PASSWORD} TEXT,

    );""".trimIndent()

    private val deleteTableUser = "drop table if exists ${DatabaseConstants.USER.TABLE_NAME}"



    override fun onCreate(sqlLite: SQLiteDatabase) {
        sqlLite.execSQL(createTableUser)
    }

    override fun onUpgrade(sqlLite: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        sqlLite.execSQL(deleteTableUser)
        sqlLite.execSQL(createTableUser)
    }


}