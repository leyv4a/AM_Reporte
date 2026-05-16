package com.example.reporteciudadano.models

import com.google.gson.annotations.SerializedName

/**
 * ReporteResponse - Modelo de datos para la respuesta de la API
 * Gson convertirá el JSON de respuesta a este objeto
 */
data class ReporteResponse(

    @SerializedName("success")
    val success: Boolean?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("id")
    val id: Int?
)
