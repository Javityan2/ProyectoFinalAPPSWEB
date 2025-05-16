package mx.itson.reporteciudadanopf.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*
import android.Manifest
import androidx.core.app.ActivityCompat
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource

/**
 * Clase utilitaria para manejar la obtención de la ubicación del dispositivo.
 * Utiliza Google Play Services Location API para obtener actualizaciones de ubicación.
 *
 * @property context Contexto de la aplicación
 */
class LocationManager(private val context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val geocoder = Geocoder(context, Locale.getDefault())

    /**
     * Obtiene actualizaciones de ubicación como un Flow.
     * La ubicación se actualiza cada 10 segundos o cuando el dispositivo se mueve 10 metros.
     *
     * @return Un Flow que emite objetos Location con la ubicación actual
     */
    fun getLocationUpdates(): Flow<Location> = callbackFlow {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000 // 10 segundos
            fastestInterval = 5000 // 5 segundos
            smallestDisplacement = 10f // 10 metros
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    trySend(location)
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }

        awaitClose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun getLastLocation(): Location? {
        return try {
            fusedLocationClient.lastLocation.await()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getAddressFromLocation(location: Location): String = withContext(Dispatchers.IO) {
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )

            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                val addressParts = mutableListOf<String>()

                // Agregar cada línea de la dirección si está disponible
                for (i in 0..address.maxAddressLineIndex) {
                    address.getAddressLine(i)?.let { addressParts.add(it) }
                }

                if (addressParts.isNotEmpty()) {
                    addressParts.joinToString(", ")
                } else {
                    "Lat: ${location.latitude}, Lng: ${location.longitude}"
                }
            } else {
                "Lat: ${location.latitude}, Lng: ${location.longitude}"
            }
        } catch (e: IOException) {
            "Error al obtener la dirección: ${e.message}"
        } catch (e: Exception) {
            "Lat: ${location.latitude}, Lng: ${location.longitude}"
        }
    }

    suspend fun getCurrentLocation(): Pair<Double, Double> = withContext(Dispatchers.IO) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            throw SecurityException("Se requiere permiso de ubicación")
        }

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            throw Exception("El GPS está desactivado")
        }

        try {
            val location = fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).await()

            if (location != null) {
                Pair(location.latitude, location.longitude)
            } else {
                throw Exception("No se pudo obtener la ubicación")
            }
        } catch (e: Exception) {
            throw Exception("Error al obtener la ubicación: ${e.message}")
        }
    }
} 