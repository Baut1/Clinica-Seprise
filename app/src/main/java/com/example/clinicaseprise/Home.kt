package com.example.clinicaseprise

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dbHelper = AccountsDatabaseHelper(this)
        val email = intent.getStringExtra("USER_EMAIL")

        if (email != null) {
            val userName = dbHelper.getUser(email)
            val welcomeTextView = findViewById<TextView>(R.id.txtBienvenida)

            if (userName != null) {
                welcomeTextView.text = getString(R.string.home_lbl_bienvenida, userName)
            } else {
                welcomeTextView.text = getString(R.string.home_lbl_bienvenida_not_found)
            }
        }
    }
}