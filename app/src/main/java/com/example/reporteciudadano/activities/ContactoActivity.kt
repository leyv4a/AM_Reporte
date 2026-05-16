package com.example.reporteciudadano.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.reporteciudadano.R

/**
 * ContactoActivity - Pantalla con información de contacto
 * Permite abrir Maps, correo y teléfono con un toque
 */
class ContactoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacto)

        // Mostrar flecha de regreso en el ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Contacto"

        // Referencias a los layouts que funcionan como botones de acción
        val layoutDireccion: LinearLayout = findViewById(R.id.layoutDireccion)
        val layoutCorreo: LinearLayout = findViewById(R.id.layoutCorreo)
        val layoutTelefono: LinearLayout = findViewById(R.id.layoutTelefono)

        // Al tocar la dirección, abrir Google Maps
        layoutDireccion.setOnClickListener {
            abrirGoogleMaps()
        }

        // Al tocar el correo, abrir la app de correo
        layoutCorreo.setOnClickListener {
            abrirCorreo()
        }

        // Al tocar el teléfono, abrir el marcador
        layoutTelefono.setOnClickListener {
            abrirMarcador()
        }
    }

    /**
     * Abrir Google Maps con la dirección del municipio
     * Usamos ACTION_VIEW con una URI de Google Maps
     */
    private fun abrirGoogleMaps() {
        val direccion = "Palacio Municipal, Centro, Guaymas, Sonora, México"
        // Uri.encode convierte los espacios y caracteres especiales para la URL
        val uri = Uri.parse("geo:0,0?q=${Uri.encode(direccion)}")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.google.android.apps.maps")

        // Verificar si Google Maps está instalado, si no usar el navegador
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            // Si no tiene Google Maps, abrir en el navegador
            val intentWeb = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://maps.google.com/?q=${Uri.encode(direccion)}"))
            startActivity(intentWeb)
        }
    }

    /**
     * Abrir la aplicación de correo electrónico
     * Usamos ACTION_SENDTO con el esquema "mailto:"
     */
    private fun abrirCorreo() {
        val correo = "reportes@municipio.gob.mx"
        val uri = Uri.parse("mailto:$correo")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra(Intent.EXTRA_SUBJECT, "Reporte Ciudadano")

        // Verificar si hay una app de correo disponible
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            android.widget.Toast.makeText(
                this,
                "No se encontró ninguna aplicación de correo",
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Abrir el marcador telefónico
     * Usamos ACTION_DIAL para que el usuario confirme antes de llamar
     */
    private fun abrirMarcador() {
        val telefono = "6222213000"
        val uri = Uri.parse("tel:$telefono")
        val intent = Intent(Intent.ACTION_DIAL, uri)
        startActivity(intent)
    }

    /**
     * Manejar el botón de regresar en el ActionBar
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
