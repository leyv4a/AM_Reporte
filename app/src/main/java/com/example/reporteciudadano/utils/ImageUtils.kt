package com.example.reporteciudadano.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.InputStream


object ImageUtils {

    /**

     * @param context Contexto de la Activity
     * @param uri URI de la imagen seleccionada en la galería
     * @return String en Base64 o null si ocurrió un error
     */
    fun convertirImagenABase64(context: Context, uri: Uri): String? {
        return try {
            // Abrir el stream de la imagen
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)

            // Decodificar como Bitmap
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            if (bitmap != null) {
                // Comprimir la imagen para reducir el tamaño del JSON
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
                val byteArray = outputStream.toByteArray()

                // Convertir a Base64
                Base64.encodeToString(byteArray, Base64.DEFAULT)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Obtiene el nombre del archivo a partir de su URI
     * Se muestra en la pantalla para que el usuario sepa qué imagen eligió
     *
     * @param context Contexto de la Activity
     * @param uri URI de la imagen seleccionada
     * @return Nombre del archivo o "imagen_seleccionada" si no se puede obtener
     */
    fun obtenerNombreArchivo(context: Context, uri: Uri): String {
        var nombre = "imagen_seleccionada"

        try {
            // Usar ContentResolver para obtener el nombre del archivo
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (index >= 0) {
                        nombre = it.getString(index) ?: nombre
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return nombre
    }
}
