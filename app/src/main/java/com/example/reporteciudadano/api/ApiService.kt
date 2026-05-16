package com.example.reporteciudadano.api

import com.example.reporteciudadano.models.ReporteRequest
import com.example.reporteciudadano.models.ReporteResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * ApiService - Define los endpoints de la API REST
 * Retrofit usará esta interfaz para hacer las llamadas HTTP
 */
interface ApiService {

    /**
     * Endpoint para enviar un nuevo reporte ciudadano
     * POST -> https://mcaconsultores.com.mx/apireporte/reporte.php
     */
    @Headers(
        "Authorization: Bearer a0f4dcad-5903-482f-8982-88ec8bc6156e",
        "Content-Type: application/json"
    )
    @POST("reporte.php")
    fun enviarReporte(@Body reporte: ReporteRequest): Call<ReporteResponse>
}
