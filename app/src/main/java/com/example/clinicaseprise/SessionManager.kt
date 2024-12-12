package com.example.clinicaseprise

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)

    companion object {
        const val USER_ID_KEY = "userId"
    }

    fun saveUserId(userId: Int) {
        val editor = prefs.edit()
        editor.putInt(USER_ID_KEY, userId)
        editor.apply()
    }

    fun getUserId(): Int {
        return prefs.getInt(USER_ID_KEY, -1)
    }

    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}