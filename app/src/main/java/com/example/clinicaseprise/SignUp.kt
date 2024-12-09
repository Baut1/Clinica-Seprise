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

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val txtPassword = findViewById<EditText>(R.id.txtPassword)
        val txtName = findViewById<EditText>(R.id.txtName)

        val btnRegistro = findViewById<Button>(R.id.btnRegistro)
        val btnInicioSesion = findViewById<Button>(R.id.btnInicioSesion)

        val dbHelper = AccountsDatabaseHelper(this)

        btnRegistro.setOnClickListener {
            val email = txtEmail.text.toString()
            val password = txtPassword.text.toString()
            val name = txtName.text.toString()

            if (email.isBlank() || password.isBlank() || name.isBlank()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val result = dbHelper.addUser(email, password, name)

                if (result > 0) {
                    Toast.makeText(this, "Usuario guardado con éxito", Toast.LENGTH_SHORT).show()
                    txtEmail.text.clear()
                    txtPassword.text.clear()
                    txtName.text.clear()
                } else {
                    Toast.makeText(this, "Error al guardar el usuario", Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        }

        btnInicioSesion.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}