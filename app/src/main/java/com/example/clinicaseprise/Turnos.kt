package com.example.clinicaseprise

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Turnos : BaseActivity() {
    override fun getLayoutResourceId(): Int = R.layout.activity_turnos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sessionManager = SessionManager(this)
        val userId = sessionManager.getUserId()

        if (userId != -1) {
            val dbHelper = AppointmentsDatabaseHelper(this)
            val appointments = dbHelper.getAppointmentsByUser(userId)

            val appointmentList = appointments.map { "${it["especialidad"]} - ${it["fecha"]} ${it["hora"]}" }

            val listView: ListView = findViewById(R.id.listViewAppointments)
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, appointmentList)
            listView.adapter = adapter
        } else {
            Toast.makeText(this, "userId no disponible", Toast.LENGTH_SHORT).show()
        }
    }
}