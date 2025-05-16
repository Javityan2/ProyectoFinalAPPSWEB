package mx.itson.reporteciudadanopf.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

object PermissionHandler {
    // Permisos de cámara y galería
    val cameraPermission = Manifest.permission.CAMERA
    val storagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // Permisos de ubicación
    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    // Verificar si se tienen los permisos de cámara
    fun hasCameraPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            cameraPermission
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Verificar si se tienen los permisos de almacenamiento
    fun hasStoragePermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            storagePermission
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Verificar si se tienen los permisos de ubicación
    fun hasLocationPermissions(context: Context): Boolean {
        return locationPermissions.all { permission ->
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    // Obtener los permisos que faltan
    fun getMissingPermissions(context: Context): Array<String> {
        val missingPermissions = mutableListOf<String>()

        if (!hasCameraPermission(context)) {
            missingPermissions.add(cameraPermission)
        }

        if (!hasStoragePermission(context)) {
            missingPermissions.add(storagePermission)
        }

        if (!hasLocationPermissions(context)) {
            missingPermissions.addAll(locationPermissions)
        }

        return missingPermissions.toTypedArray()
    }
} 