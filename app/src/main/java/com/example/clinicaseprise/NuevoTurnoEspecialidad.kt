package com.example.clinicaseprise

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NuevoTurnoEspecialidad : BaseActivity() {
    override fun getLayoutResourceId(): Int = R.layout.activity_nuevo_turno_especialidad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listView: ListView = findViewById(R.id.listViewEspecialidades)

        val especialidades = listOf(
            "Cardiología", "Cirugía Plástica", "Dermatología", "Endocrinología",
            "Gastroenterología", "Ginecología", "Hematología", "Infectología",
            "Medicina General", "Nefrología", "Neurología", "Oftalmología",
            "Oncología", "Otorrinolaringología", "Pediatría", "Psiquiatría",
            "Reumatología", "Traumatología", "Urología"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            especialidades
        )
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val especialidadSeleccionada = especialidades[position]
            val intent = Intent(this, NuevoTurnoDatePicker::class.java)
            intent.putExtra("especialidad", especialidadSeleccionada)
            startActivity(intent)
        }
    }
}