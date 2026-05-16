package com.example.reporteciudadano.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.reporteciudadano.R
import com.example.reporteciudadano.api.ApiClient
import com.example.reporteciudadano.models.ReporteRequest
import com.example.reporteciudadano.models.ReporteResponse
import com.example.reporteciudadano.utils.ImageUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReporteActivity : AppCompatActivity() {

    // Código para identificar el resultado de seleccionar imagen
    private val PICK_IMAGE_REQUEST = 100
    private val PERMISSION_REQUEST = 101

    // URI de la imagen seleccionada (puede ser null si no eligió imagen)
    private var imagenUri: Uri? = null

    // Referencias a los campos del formulario
    private lateinit var etNombre: EditText
    private lateinit var etDireccion: EditText
    private lateinit var etCelular: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var spinnerColonia: Spinner
    private lateinit var spinnerTipo: Spinner
    private lateinit var btnSeleccionarImagen: Button
    private lateinit var tvNombreImagen: TextView
    private lateinit var btnEnviarReporte: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reporte)

        // Mostrar flecha de regreso en el ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Crear Reporte"

        // Inicializar todas las vistas del layout
        inicializarVistas()

        // Llenar el Spinner de colonias con la lista predefinida
        configurarSpinnerColonias()

        // Llenar el Spinner de tipos de reporte
        configurarSpinnerTipos()

        // Botón para abrir la galería y seleccionar imagen
        btnSeleccionarImagen.setOnClickListener {
            solicitarPermisoYAbrirGaleria()
        }

        // Botón para enviar el reporte a la API
        btnEnviarReporte.setOnClickListener {
            if (validarFormulario()) {
                enviarReporte()
            }
        }
    }

    /**
     * Inicializar todas las referencias a las vistas del XML
     */
    private fun inicializarVistas() {
        etNombre = findViewById(R.id.etNombre)
        etDireccion = findViewById(R.id.etDireccion)
        etCelular = findViewById(R.id.etCelular)
        etCorreo = findViewById(R.id.etCorreo)
        etDescripcion = findViewById(R.id.etDescripcion)
        spinnerColonia = findViewById(R.id.spinnerColonia)
        spinnerTipo = findViewById(R.id.spinnerTipo)
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen)
        tvNombreImagen = findViewById(R.id.tvNombreImagen)
        btnEnviarReporte = findViewById(R.id.btnEnviarReporte)
    }

    /**
     * Configurar el Spinner con la lista de colonias
     */
    private fun configurarSpinnerColonias() {
        val colonias = listOf(
            "Selecciona una colonia",
            "CENTRO",
            "LUIS DONALDO COLOSIO",
            "GUADALUPE",
            "FATIMA",
            "LAS PALMAS",
            "MIRAMAR"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, colonias)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerColonia.adapter = adapter
    }

    /**
     * Configurar el Spinner con los tipos de reporte
     */
    private fun configurarSpinnerTipos() {
        val tipos = listOf(
            "Selecciona tipo de reporte",
            "BACHE",
            "ALUMBRADO PÚBLICO",
            "BASURA O ESCOMBRO",
            "FUMIGACIÓN O PLAGAS",
            "FUGAS O DRENAJE",
            "OTRO ASUNTO",
            "ANIMALES CALLEJEROS O EN SITUACIÓN DE ABANDONO"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTipo.adapter = adapter
    }

    /**
     * Solicitar permiso de galería y abrir selector de imágenes
     */
    private fun solicitarPermisoYAbrirGaleria() {
        // En Android 13+ se usa READ_MEDIA_IMAGES, en versiones anteriores READ_EXTERNAL_STORAGE
        val permiso = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(this, permiso) == PackageManager.PERMISSION_GRANTED) {
            abrirGaleria()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permiso), PERMISSION_REQUEST)
        }
    }

    /**
     * Abrir la galería para seleccionar una imagen
     */
    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    /**
     * Resultado del permiso solicitado
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirGaleria()
            } else {
                Toast.makeText(this, "Permiso denegado para acceder a la galería", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Resultado de la selección de imagen en la galería
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imagenUri = data.data

            // Obtener solo el nombre del archivo para mostrarlo
            imagenUri?.let { uri ->
                val nombreArchivo = ImageUtils.obtenerNombreArchivo(this, uri)
                tvNombreImagen.text = nombreArchivo
            }
        }
    }

    /**
     * Validar que los campos obligatorios estén llenos y el correo sea válido
     */
    private fun validarFormulario(): Boolean {
        val nombre = etNombre.text.toString().trim()
        val direccion = etDireccion.text.toString().trim()
        val celular = etCelular.text.toString().trim()
        val correo = etCorreo.text.toString().trim()
        val descripcion = etDescripcion.text.toString().trim()

        // Validar que el nombre no esté vacío
        if (nombre.isEmpty()) {
            etNombre.error = "El nombre es obligatorio"
            etNombre.requestFocus()
            return false
        }

        // Validar que la colonia esté seleccionada (no la opción por default)
        if (spinnerColonia.selectedItemPosition == 0) {
            Toast.makeText(this, "Selecciona una colonia", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validar que la dirección no esté vacía
        if (direccion.isEmpty()) {
            etDireccion.error = "La dirección es obligatoria"
            etDireccion.requestFocus()
            return false
        }

        // Validar que el celular no esté vacío
        if (celular.isEmpty()) {
            etCelular.error = "El celular es obligatorio"
            etCelular.requestFocus()
            return false
        }

        // Validar que el correo no esté vacío
        if (correo.isEmpty()) {
            etCorreo.error = "El correo es obligatorio"
            etCorreo.requestFocus()
            return false
        }

        // Validar que el correo tenga formato válido (contiene @ y punto)
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            etCorreo.error = "Ingresa un correo válido"
            etCorreo.requestFocus()
            return false
        }

        // Validar que el tipo de reporte esté seleccionado
        if (spinnerTipo.selectedItemPosition == 0) {
            Toast.makeText(this, "Selecciona el tipo de reporte", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validar que la descripción no esté vacía
        if (descripcion.isEmpty()) {
            etDescripcion.error = "La descripción es obligatoria"
            etDescripcion.requestFocus()
            return false
        }

        return true
    }

    /**
     * Construir el objeto del reporte y enviarlo a la API mediante Retrofit
     */
    private fun enviarReporte() {
        // Obtener los valores del formulario
        val nombre = etNombre.text.toString().trim()
        val colonia = spinnerColonia.selectedItem.toString()
        val direccion = etDireccion.text.toString().trim()
        val celular = etCelular.text.toString().trim()
        val correo = etCorreo.text.toString().trim()
        val tipo = spinnerTipo.selectedItem.toString()
        val descripcion = etDescripcion.text.toString().trim()

        // Convertir imagen a Base64
        var imagenBase64: String? = null
        imagenUri?.let { uri ->
            val base64 = ImageUtils.convertirImagenABase64(this, uri)
            if (base64 != null) {
                imagenBase64 = "data:image/png;base64,$base64"
            }
        }

        // Crear el objeto con todos los datos del reporte
        val reporte = ReporteRequest(
            nombre_interesado = nombre,
            direccion = direccion,
            colonia = colonia,
            celular = celular,
            correo = correo,
            tipo = tipo,
            descripcion = descripcion,
            imagen = imagenBase64
        )

        // Deshabilitar el botón para evitar doble envío
        btnEnviarReporte.isEnabled = false
        btnEnviarReporte.text = "Enviando..."

        // Llamar a la API usando Retrofit
        val apiService = ApiClient.getApiService()
        val call = apiService.enviarReporte(reporte)

        call.enqueue(object : Callback<ReporteResponse> {
            override fun onResponse(call: Call<ReporteResponse>, response: Response<ReporteResponse>) {
                // Rehabilitar el botón
                btnEnviarReporte.isEnabled = true
                btnEnviarReporte.text = "Enviar Reporte"

                if (response.isSuccessful) {
                    // Reporte enviado exitosamente
                    Toast.makeText(
                        this@ReporteActivity,
                        "¡Reporte enviado exitosamente!",
                        Toast.LENGTH_LONG
                    ).show()
                    // Limpiar el formulario después de enviar
                    limpiarFormulario()
                } else {
                    // Error en la respuesta del servidor
                    Toast.makeText(
                        this@ReporteActivity,
                        "Error al enviar: ${response.code()} - ${response.message()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ReporteResponse>, t: Throwable) {
                // Error de conexión o de red
                btnEnviarReporte.isEnabled = true
                btnEnviarReporte.text = "Enviar Reporte"

                Toast.makeText(
                    this@ReporteActivity,
                    "Error de conexión: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    /**
     * Limpiar todos los campos del formulario después de un envío exitoso
     */
    private fun limpiarFormulario() {
        etNombre.setText("")
        etDireccion.setText("")
        etCelular.setText("")
        etCorreo.setText("")
        etDescripcion.setText("")
        spinnerColonia.setSelection(0)
        spinnerTipo.setSelection(0)
        imagenUri = null
        tvNombreImagen.text = "Ninguna imagen seleccionada"
    }

    /**
     * Manejar el botón de regresar en el ActionBar
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
