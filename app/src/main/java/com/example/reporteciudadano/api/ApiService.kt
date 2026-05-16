package com.example.reporteciudadano.api

import com.example.reporteciudadano.models.ReporteRequest
import com.example.reporteciudadano.models.ReporteResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiService {


    @Headers(
        "Authorization: Bearer a0f4dcad-5903-482f-8982-88ec8bc6156e",
        "Content-Type: application/json"
    )
    @POST("reporte.php")
    fun enviarReporte(@Body reporte: ReporteRequest): Call<ReporteResponse>
}
