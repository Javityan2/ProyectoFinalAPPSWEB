package mx.itson.reporteciudadanopf.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * Clase utilitaria para manejar la verificación de permisos de la aplicación.
 * Proporciona métodos para verificar si se tienen los permisos necesarios.
 *
 * @property context Contexto de la aplicación
 */
class PermissionManager(private val context: Context) {
    /**
     * Verifica si se tienen todos los permisos necesarios para la aplicación.
     * Los permisos incluyen: cámara, ubicación y almacenamiento.
     *
     * @return true si se tienen todos los permisos, false en caso contrario
     */
    fun hasAllPermissions(): Boolean {
        return REQUIRED_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        /**
         * Lista de permisos requeridos por la aplicación.
         */
        val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
} 