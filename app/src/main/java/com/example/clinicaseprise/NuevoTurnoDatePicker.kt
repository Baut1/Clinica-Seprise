package com.example.clinicaseprise

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NuevoTurnoDatePicker : BaseActivity() {
    override fun getLayoutResourceId(): Int = R.layout.activity_nuevo_turno_date_picker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val datePicker: DatePicker = findViewById(R.id.datePickerNuevoTurno)
        val timePicker: TimePicker = findViewById(R.id.timePickerNuevoTurno)
        val btnConfirmar: Button = findViewById(R.id.btnConfirmarTurno)

        val especialidad = intent.getStringExtra("especialidad")

        btnConfirmar.setOnClickListener {
            val day = datePicker.dayOfMonth
            val month = datePicker.month + 1
            val year = datePicker.year
            val hour = timePicker.hour
            val minute = timePicker.minute

            val fecha = "$year-$month-$day"
            val hora = String.format("%02d:%02d", hour, minute)

            val dbHelper = AppointmentsDatabaseHelper(this)

            val sessionManager = SessionManager(this)
            val userId = sessionManager.getUserId()

            val result = dbHelper.addAppointment(userId, especialidad ?: "", fecha, hora)

            if (result != -1L) {
                Toast.makeText(this, "Turno confirmado: $fecha $hora", Toast.LENGTH_LONG).show()
                val intent = Intent(this, Turnos::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Error al confirmar el turno", Toast.LENGTH_SHORT).show()
            }
        }
    }
}