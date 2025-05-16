package mx.itson.reporteciudadanopf.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Utilidades para el procesamiento y manejo de imágenes en la aplicación.
 * Proporciona funciones para convertir imágenes a base64 y optimizarlas.
 */
object ImageUtils {
    private const val MAX_IMAGE_SIZE = 640
    private const val COMPRESSION_QUALITY = 100 // PNG no usa compresión de calidad

    /**
     * Convierte una imagen desde una URI a formato base64.
     * El proceso incluye:
     * 1. Lectura de la imagen original
     * 2. Optimización del tamaño
     * 3. Compresión manteniendo la calidad
     * 4. Conversión a base64
     *
     * @param context Contexto de la aplicación
     * @param uri URI de la imagen a convertir
     * @return String Imagen en formato base64
     * @throws IOException Si hay problemas al leer o procesar la imagen
     */
    fun uriToBase64(context: Context, uri: Uri): String {
        var inputStream: InputStream? = null
        var bitmap: Bitmap? = null
        var compressedBitmap: Bitmap? = null
        
        try {
            // Primera pasada: obtener dimensiones
            inputStream = context.contentResolver.openInputStream(uri)
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream?.close()
            
            // Calcular el factor de escala
            var scale = 1
            if (options.outHeight > MAX_IMAGE_SIZE || options.outWidth > MAX_IMAGE_SIZE) {
                val heightRatio = options.outHeight.toFloat() / MAX_IMAGE_SIZE
                val widthRatio = options.outWidth.toFloat() / MAX_IMAGE_SIZE
                scale = Math.max(heightRatio, widthRatio).toInt()
            }
            
            // Segunda pasada: decodificar con el factor de escala
            inputStream = context.contentResolver.openInputStream(uri)
            val decodeOptions = BitmapFactory.Options().apply {
                inSampleSize = scale
                inPreferredConfig = Bitmap.Config.ARGB_8888 // PNG necesita ARGB_8888
            }
            bitmap = BitmapFactory.decodeStream(inputStream, null, decodeOptions)
            
            // Comprimir la imagen
            compressedBitmap = compressImage(bitmap!!)
            
            // Convertir a base64
            val outputStream = ByteArrayOutputStream()
            compressedBitmap.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY, outputStream)
            val base64Image = Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
            
            // Retornar en el formato esperado por el servidor
            return "data:image/png;base64,$base64Image"
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        } finally {
            try {
                inputStream?.close()
                bitmap?.recycle()
                compressedBitmap?.recycle()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Comprime una imagen bitmap manteniendo su calidad.
     * La imagen se redimensiona si excede el tamaño máximo permitido.
     *
     * @param bitmap Bitmap original a comprimir
     * @return Bitmap Imagen comprimida
     */
    private fun compressImage(bitmap: Bitmap): Bitmap {
        var width = bitmap.width
        var height = bitmap.height
        
        if (width > height && width > MAX_IMAGE_SIZE) {
            height = (height * MAX_IMAGE_SIZE / width).toInt()
            width = MAX_IMAGE_SIZE
        } else if (height > MAX_IMAGE_SIZE) {
            width = (width * MAX_IMAGE_SIZE / height).toInt()
            height = MAX_IMAGE_SIZE
        }
        
        return if (width != bitmap.width || height != bitmap.height) {
            Bitmap.createScaledBitmap(bitmap, width, height, true)
        } else {
            bitmap
        }
    }

    /**
     * Calcula el factor de escala necesario para redimensionar una imagen.
     * Asegura que la imagen no exceda el tamaño máximo permitido.
     *
     * @param width Ancho original de la imagen
     * @param height Alto original de la imagen
     * @return Int Factor de escala a aplicar
     */
    private fun calculateScaleFactor(width: Int, height: Int): Int {
        // ... existing code ...
        return 1 // Placeholder return, actual implementation needed
    }
} 