package com.example.clinicaseprise

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())

        // Initialize BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    if (this !is Home) {
                        startActivity(Intent(this, Home::class.java))
                    }
                    true
                }
                R.id.nav_nuevo_turno -> {
                    if (this !is NuevoTurnoEspecialidad) {
                        startActivity(Intent(this, NuevoTurnoEspecialidad::class.java))
                    }
                    true
                }
                R.id.nav_configuracion -> {
                    if (this !is Configuracion) {
                        startActivity(Intent(this, Configuracion::class.java))
                    }
                    true
                }
                else -> false
            }
        }

        // Highlight the current menu item
        highlightCurrentMenuItem(bottomNavigationView)
    }

    // Abstract function to enforce layout resource ID in child activities
    protected abstract fun getLayoutResourceId(): Int

    private fun highlightCurrentMenuItem(bottomNavigationView: BottomNavigationView?) {
        bottomNavigationView?.menu?.let { menu ->
            when (this) {
                is Home -> menu.findItem(R.id.nav_home)?.isChecked = true
                is NuevoTurnoEspecialidad -> menu.findItem(R.id.nav_nuevo_turno)?.isChecked = true
                is Configuracion -> menu.findItem(R.id.nav_configuracion)?.isChecked = true
            }
        }
    }
}