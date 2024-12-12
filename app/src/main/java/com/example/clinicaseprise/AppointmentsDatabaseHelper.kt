package com.example.clinicaseprise

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppointmentsDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "TURNOS.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_TURNOS = "Turnos"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USER_ID = "userId"
        private const val COLUMN_ESPECIALIDAD = "especialidad"
        private const val COLUMN_FECHA = "fecha"
        private const val COLUMN_HORA = "hora"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableTurnos = """
            CREATE TABLE $TABLE_TURNOS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USER_ID INTEGER NOT NULL,
                $COLUMN_ESPECIALIDAD TEXT NOT NULL,
                $COLUMN_FECHA TEXT NOT NULL,
                $COLUMN_HORA TEXT NOT NULL,
                FOREIGN KEY($COLUMN_USER_ID) REFERENCES Accounts($COLUMN_ID)
            )
        """.trimIndent()
        db.execSQL(createTableTurnos)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TURNOS")
        onCreate(db)
    }

    fun addAppointment(userId: Int, especialidad: String, fecha: String, hora: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_ID, userId)
            put(COLUMN_ESPECIALIDAD, especialidad)
            put(COLUMN_FECHA, fecha)
            put(COLUMN_HORA, hora)
        }
        val result = db.insert(TABLE_TURNOS, null, values)
        db.close()
        return result
    }

    fun getAppointmentsByUser(userId: Int): List<Map<String, String>> {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_TURNOS WHERE $COLUMN_USER_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))
        val turnos = mutableListOf<Map<String, String>>()

        if (cursor.moveToFirst()) {
            do {
                val turno = mapOf(
                    COLUMN_ESPECIALIDAD to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ESPECIALIDAD)),
                    COLUMN_FECHA to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA)),
                    COLUMN_HORA to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HORA))
                )
                turnos.add(turno)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return turnos
    }
}