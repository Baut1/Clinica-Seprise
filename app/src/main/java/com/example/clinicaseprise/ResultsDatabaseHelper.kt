package com.example.clinicaseprise

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ResultsDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "RESULTS.db"
        private const val DATABASE_VERSION = 2

        private const val TABLE_ESTUDIOS = "Estudios"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USER_ID = "userId"
        private const val COLUMN_FECHA = "fecha"
        private const val COLUMN_TITULO = "titulo"
        private const val COLUMN_RESULTADO = "resultado"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableEstudios = """
            CREATE TABLE $TABLE_ESTUDIOS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USER_ID INTEGER NOT NULL,
                $COLUMN_FECHA TEXT NOT NULL,
                $COLUMN_TITULO TEXT NOT NULL,
                $COLUMN_RESULTADO TEXT NOT NULL,
                FOREIGN KEY($COLUMN_USER_ID) REFERENCES Accounts($COLUMN_ID)
            )
        """.trimIndent()
        db.execSQL(createTableEstudios)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ESTUDIOS")
        onCreate(db)
    }

    fun getStudiesByUser(userId: Int): List<Map<String, String>> {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_ESTUDIOS WHERE $COLUMN_USER_ID = ? ORDER BY $COLUMN_FECHA DESC"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))
        val studies = mutableListOf<Map<String, String>>()

        if (cursor.moveToFirst()) {
            do {
                val study = mapOf(
                    COLUMN_FECHA to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA)),
                    COLUMN_TITULO to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO)),
                    COLUMN_RESULTADO to cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESULTADO))
                )
                studies.add(study)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return studies
    }

    fun addSampleData() {
        val studies = listOf(
            Triple("2024-06-11", "Perfil lipídico", """
        Colesterol total: 190 mg/dL
        LDL (malo): 120 mg/dL
        HDL (bueno): 50 mg/dL
        Triglicéridos: 130 mg/dL
        VLDL (lipoproteínas de muy baja densidad): 25 mg/dL
        Relación colesterol total/HDL: 3.8
        Interpretación:
        - LDL levemente elevado. Se recomienda un ajuste en la dieta, preferentemente reduciendo la ingesta de grasas saturadas.
        - HDL dentro del rango normal, lo que indica un buen nivel de colesterol "bueno".
        - Triglicéridos dentro del rango normal, lo que sugiere un riesgo bajo de enfermedades cardiovasculares.
        - Mantener un estilo de vida saludable, con ejercicios regulares, control del peso y alimentación balanceada.
    """.trimIndent()),

            Triple("2024-05-03", "Hemograma completo", """
        Hemoglobina: 13.5 g/dL
        Hematocrito: 40%
        Leucocitos: 5,000/mm³
        Neutrófilos: 65% (3,250/mm³)
        Linfocitos: 30% (1,500/mm³)
        Plaquetas: 250,000/mm³
        Recuento de glóbulos rojos: 4.5 millones/mm³
        Volumen corpuscular medio (VCM): 88 fL
        Concentración media de hemoglobina (MCHC): 33 g/dL
        Interpretación:
        - Hemoglobina y hematocrito dentro de valores normales, indicando una adecuada capacidad de transporte de oxígeno.
        - Leucocitos dentro del rango normal, pero el aumento relativo de neutrófilos puede indicar una respuesta a una posible infección bacteriana.
        - Recuento de plaquetas normal, lo que sugiere una adecuada función de coagulación sanguínea.
        - Los linfocitos dentro del rango normal, indicando una respuesta inmune adecuada.
        - En general, el hemograma es normal, pero se recomienda seguimiento en caso de síntomas relacionados con infecciones o trastornos sanguíneos.
    """.trimIndent())
        )

        val db = this.writableDatabase
        studies.forEach { (fecha, titulo, resultado) ->
            val values = ContentValues().apply {
                put(COLUMN_USER_ID, 3)
                put(COLUMN_FECHA, fecha)
                put(COLUMN_TITULO, titulo)
                put(COLUMN_RESULTADO, resultado)
            }
            db.insert(TABLE_ESTUDIOS, null, values)
        }
        db.close()
    }

}
