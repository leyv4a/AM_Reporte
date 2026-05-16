package com.example.reporteciudadano.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.reporteciudadano.R

/**
 * MainActivity - Pantalla principal de la aplicación
 * Muestra dos botones: Crear Reporte y Contacto
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia al botón "Crear Reporte"
        val btnCrearReporte: Button = findViewById(R.id.btnCrearReporte)

        // Referencia al botón "Contacto"
        val btnContacto: Button = findViewById(R.id.btnContacto)

        // Al presionar "Crear Reporte" abrimos ReporteActivity
        btnCrearReporte.setOnClickListener {
            val intent = Intent(this, ReporteActivity::class.java)
            startActivity(intent)
        }

        // Al presionar "Contacto" abrimos ContactoActivity
        btnContacto.setOnClickListener {
            val intent = Intent(this, ContactoActivity::class.java)
            startActivity(intent)
        }
    }
}
