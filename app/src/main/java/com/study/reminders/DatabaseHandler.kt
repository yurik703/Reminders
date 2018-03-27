package com.study.reminders

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.content.ContentValues
import android.util.Log
import java.util.ArrayList

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, "reminderDb.db", null, 1) {
    val TAG = "DatabaseHandler"
    val TABLE = "remindersTable"

    companion object {
        val ID: String = "_id"
        val TEXT: String = "TEXT"
    }

    val DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE + " (" +
                    "$ID integer PRIMARY KEY autoincrement," +
                    "$TEXT text"+
                    ")"

    fun set(text: String) {
        val values = ContentValues()
        values.put(TEXT, text)
        writableDatabase.insert(TABLE, null, values)
    }

    fun get() : ArrayList<String> {
        val remindersList = ArrayList<String>()
        val cursor = readableDatabase
                .query(TABLE, arrayOf(ID, TEXT), null, null, null, null, null)

        var reminder: String
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                reminder = cursor.getString(cursor.getColumnIndex("TEXT"))

                remindersList.add(reminder)
                cursor.moveToNext()
            }
        }
        return remindersList
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(TAG, "Creating: $DATABASE_CREATE")
        db.execSQL(DATABASE_CREATE)
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
    }

}
