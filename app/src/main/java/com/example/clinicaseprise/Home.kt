package com.example.clinicaseprise

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Home : BaseActivity() {
    override fun getLayoutResourceId(): Int = R.layout.activity_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dbHelper = AccountsDatabaseHelper(this)
        val sessionManager = SessionManager(this)
        val userId = sessionManager.getUserId()

        val userName = dbHelper.getName(userId)
        val welcomeTextView = findViewById<TextView>(R.id.txtBienvenida)

        if (userName != null) {
            welcomeTextView.text = getString(R.string.home_lbl_bienvenida, userName)
        } else {
            welcomeTextView.text = getString(R.string.home_lbl_bienvenida_not_found)
        }

        val cardContainer = findViewById<LinearLayout>(R.id.cardContainer)

        val cardsData = listOf(
            Triple("Mis datos", R.drawable.lifesavers_avatar, Perfil::class.java),
            Triple("Resultados de laboratorio", R.drawable.lifesavers_electrocardiogram, Resultados::class.java),
            Triple("Mis turnos", R.drawable.lifesavers_serum_bag, Turnos::class.java),
            Triple("Chat Online", R.drawable.lifesavers_bust_upper_half, Chat::class.java),
            Triple("Ayuda", R.drawable.lifesavers_stethoscope, Ayuda::class.java),
        )

        for (data in cardsData) {
            val cardView = layoutInflater.inflate(R.layout.card_view_section, cardContainer, false)

            val textView = cardView.findViewById<TextView>(R.id.txtSeccion)
            val imageView = cardView.findViewById<ImageView>(R.id.imgSeccion)
            textView.text = data.first
            imageView.setImageResource(data.second)

            cardView.findViewById<CardView>(R.id.cardView).setOnClickListener {
                val intent = Intent(this, data.third)
                startActivity(intent)
            }

            cardContainer.addView(cardView)
        }
    }
}