package com.example.clinicaseprise

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class AccountsDatabaseHelper (context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    companion object {
        private val DATABASE_NAME = "ACCOUNTS.db"
        private val DATABASE_VERSION = 1
        private val TABLE_ACCOUNTS = "Accounts"
        private val COLUMN_ID = "id"
        private val COLUMN_EMAIL = "email"
        private val COLUMN_PASSWORD = "password"
        private val COLUMN_NAME = "name"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE " + TABLE_ACCOUNTS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, "
                + COLUMN_PASSWORD + " TEXT NOT NULL, "
                + COLUMN_NAME + " TEXT NOT NULL)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS)
        onCreate(db)
    }

    fun addUser(email: String, password: String, name: String): Long{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_NAME, name)
        }
        val success = db.insert(TABLE_ACCOUNTS, null, values)
        if (success == -1L) {
            Log.e("DB_ERROR", "Error al insertar registro")
        }
        db.close()
        return success
    }

    fun validateUser(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_ACCOUNTS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))
        val isValid = cursor.count > 0
        cursor.close()
        db.close()
        return isValid
    }

    fun getUserIdByEmail(email: String): Int? {
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_ID FROM $TABLE_ACCOUNTS WHERE $COLUMN_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))
        var userId: Int? = null
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        }
        cursor.close()
        db.close()
        return userId
    }

    fun getName(userId: Int): String? {
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_NAME FROM $TABLE_ACCOUNTS WHERE $COLUMN_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))
        var name: String? = null
        if (cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        }
        cursor.close()
        db.close()
        return name
    }



}