package com.example.reporteciudadano.models

import com.google.gson.annotations.SerializedName

/**
 * ReporteRequest - Modelo de datos para enviar un reporte a la API
 * Los nombres con @SerializedName deben coincidir exactamente con los del JSON de la API
 */
data class ReporteRequest(

    @SerializedName("nombre_interesado")
    val nombre_interesado: String,

    @SerializedName("direccion")
    val direccion: String,

    @SerializedName("colonia")
    val colonia: String,

    @SerializedName("celular")
    val celular: String,

    @SerializedName("correo")
    val correo: String,

    @SerializedName("tipo")
    val tipo: String,

    @SerializedName("descripcion")
    val descripcion: String,

    @SerializedName("imagen")
    val imagen: String?  // Puede ser null si no se seleccionó imagen
)
