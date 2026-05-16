# Reglas de ProGuard para ReporteCiudadano
# Mantener clases de modelos para que Gson funcione correctamente
-keep class com.example.reporteciudadano.models.** { *; }

# Mantener Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

# Mantener OkHttp
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
