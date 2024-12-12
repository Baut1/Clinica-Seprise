    package com.example.clinicaseprise

    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.activity.enableEdgeToEdge
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView

    class Resultados : BaseActivity() {
        override fun getLayoutResourceId(): Int = R.layout.activity_resultados

        private lateinit var recyclerView: RecyclerView
        private lateinit var databaseHelper: ResultsDatabaseHelper

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            recyclerView = findViewById(R.id.recyclerViewResultados)
            databaseHelper = ResultsDatabaseHelper(this)

            val sessionManager = SessionManager(this)
            val userId = sessionManager.getUserId()

            if (userId != -1) {
                val studyResults = databaseHelper.getStudiesByUser(userId)

                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = object : RecyclerView.Adapter<ResultViewHolder>() {
                    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
                        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_result, parent, false)
                        return ResultViewHolder(view)
                    }

                    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {

                        val study = studyResults[position]
                        holder.textViewFecha.text = study["fecha"]
                        holder.textViewTitulo.text = study["titulo"]
                        holder.textViewResultado.text = study["resultado"]
                    }

                    override fun getItemCount(): Int = studyResults.size
                }
            }
        }

        class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textViewFecha: TextView = itemView.findViewById(R.id.cardViewResultTextViewFecha)
            val textViewTitulo: TextView = itemView.findViewById(R.id.cardViewResultTextViewTitulo)
            val textViewResultado: TextView = itemView.findViewById(R.id.cardViewResultTextViewResultado)
        }
    }