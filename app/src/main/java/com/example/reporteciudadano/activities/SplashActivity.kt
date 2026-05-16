package com.example.reporteciudadano.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.reporteciudadano.R

/**
 * SplashActivity - Pantalla de bienvenida
 * Se muestra durante 2 segundos y luego abre MainActivity
 */
class SplashActivity : AppCompatActivity() {

    // Tiempo de duración del splash en milisegundos
    private val SPLASH_DELAY = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Usamos un Handler para esperar 2 segundos y luego ir a MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Crear el Intent para abrir MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Cerrar SplashActivity para que no vuelva con el botón Atrás
            finish()
        }, SPLASH_DELAY)
    }
}
