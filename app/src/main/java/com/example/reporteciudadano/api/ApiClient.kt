package com.example.reporteciudadano.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * ApiClient - Configuración de Retrofit
 * Aquí definimos la URL base y los ajustes de la conexión HTTP
 */
object ApiClient {

    // URL base de la API (sin el nombre del endpoint)
    private const val BASE_URL = "https://mcaconsultores.com.mx/apireporte/"

    // Instancia de Retrofit (se crea una sola vez gracias a lazy)
    private val retrofit: Retrofit by lazy {

        // Interceptor para ver los logs de red en Logcat (útil para depuración)
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        // Configurar el cliente HTTP con tiempos de espera
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)  // Tiempo máximo para conectar
            .readTimeout(30, TimeUnit.SECONDS)      // Tiempo máximo para leer
            .writeTimeout(30, TimeUnit.SECONDS)     // Tiempo máximo para escribir
            .addInterceptor(logging)                // Agregar logging
            .build()

        // Construir Retrofit con la URL base y el convertidor de JSON
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create()) // Gson convierte JSON a objetos Kotlin
            .build()
    }

    /**
     * Obtener la instancia del servicio de API
     * Se usa en las Activities para hacer las llamadas
     */
    fun getApiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
