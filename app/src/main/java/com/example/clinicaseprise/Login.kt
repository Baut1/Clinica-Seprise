package com.example.clinicaseprise

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val txtPassword = findViewById<EditText>(R.id.txtPassword)

        val btnInicioSesion = findViewById<Button>(R.id.btnInicioSesion)
        val btnRegistro = findViewById<Button>(R.id.btnRegistro)

        val dbHelper = AccountsDatabaseHelper(this)

        btnInicioSesion.setOnClickListener {
            val email = txtEmail.text.toString()
            val password = txtPassword.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Complete ambos campos", Toast.LENGTH_SHORT).show()
            } else {
                val isValid = dbHelper.validateUser(email, password)

                if (isValid) {
                    val userId = dbHelper.getUserIdByEmail(email)
                    val sessionManager = SessionManager(this)
                    if (userId != null) {
                        sessionManager.saveUserId(userId)
                    }

                    val intent = Intent(this, Home::class.java)
                    intent.putExtra("USER_EMAIL", email)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Email y/o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
            finish()
        }

        btnRegistro.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }
    }
}