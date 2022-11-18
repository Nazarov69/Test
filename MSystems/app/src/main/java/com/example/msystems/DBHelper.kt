package com.example.msystems

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "contactDb"
        const val TABLE_CONTACTS = "contacts"
        const val KEY_ID = "_id"
        const val KEY_NAME = "name"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table " + TABLE_CONTACTS + "(" + KEY_ID
                    + " integer primary key," + KEY_NAME + " text" + ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS)
        onCreate(db)
    }
}
