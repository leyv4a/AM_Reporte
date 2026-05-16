# ReporteCiudadano 📋

Aplicación Android para crear y enviar reportes ciudadanos mediante una API REST.

## Requisitos

- Android Studio o IntelliJ IDEA con plugin de Android
- Android SDK 26+
- Kotlin 1.9+
- Gradle 8.0

## Estructura del proyecto

```
app/src/main/java/com/example/reporteciudadano/
├── activities/
│   ├── SplashActivity.kt       # Pantalla de bienvenida (2 seg)
│   ├── MainActivity.kt         # Menú principal
│   ├── ReporteActivity.kt      # Formulario de reporte
│   └── ContactoActivity.kt     # Información de contacto
├── api/
│   ├── ApiService.kt           # Interfaz con los endpoints
│   └── ApiClient.kt            # Configuración de Retrofit
├── models/
│   ├── ReporteRequest.kt       # Modelo para enviar datos
│   └── ReporteResponse.kt      # Modelo de respuesta API
└── utils/
    └── ImageUtils.kt           # Utilidades para imágenes
```

## Cómo abrir el proyecto

1. Abre IntelliJ IDEA o Android Studio
2. Selecciona **File > Open**
3. Navega hasta la carpeta `ReporteCiudadano`
4. Haz clic en **OK**
5. Espera a que Gradle sincronice las dependencias
6. Ejecuta en un emulador o dispositivo físico

## Funcionalidades

- **Splash Screen**: Pantalla de bienvenida por 2 segundos
- **Crear Reporte**: Formulario completo con validaciones
- **Galería**: Seleccionar imagen y enviar en Base64
- **Contacto**: Abrir Maps, correo y marcador telefónico

## API

- **URL Base**: https://mcaconsultores.com.mx/apireporte/
- **Endpoint**: POST reporte.php
- **Auth**: Bearer Token
