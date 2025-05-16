package mx.itson.reporteciudadanopf.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Clase utilitaria para manejar la creación y gestión de archivos de imagen para la cámara.
 *
 * @property context Contexto de la aplicación
 */
class CameraManager(private val context: Context) {
    /**
     * Crea un archivo temporal para almacenar una imagen capturada por la cámara.
     * El nombre del archivo incluye una marca de tiempo para evitar colisiones.
     *
     * @return Un archivo temporal con extensión .jpg
     */
    fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir("Pictures")
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    /**
     * Obtiene la URI de un archivo para compartirlo con otras aplicaciones.
     * Utiliza FileProvider para generar una URI segura.
     *
     * @param file El archivo para el cual se generará la URI
     * @return La URI del archivo
     */
    fun getUriForFile(file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }
} 