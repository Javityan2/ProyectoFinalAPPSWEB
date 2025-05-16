package mx.itson.reporteciudadanopf.models

import android.net.Uri
import androidx.compose.runtime.Immutable
import java.util.*

/**
 * Modelo de datos que representa un reporte ciudadano.
 * Esta clase es inmutable para garantizar la consistencia de los datos.
 *
 * @property nombre_interesado Nombre del ciudadano que realiza el reporte
 * @property direccion Dirección específica del problema
 * @property colonia Colonia donde se ubica el problema
 * @property celular Número de teléfono del ciudadano
 * @property correo Correo electrónico del ciudadano
 * @property tipo Tipo de reporte
 * @property descripcion Descripción detallada del problema
 * @property imagen Imagen opcional del problema (en base64)
 */
@Immutable
data class Report(
    val nombre_interesado: String,
    val direccion: String,
    val colonia: String,
    val celular: String,
    val correo: String,
    val tipo: String,
    val descripcion: String,
    val imagen: String? = null
)

data class ReportResponse(
    val id: Int,
    val tipo: String,
    val descripcion: String,
    val direccion: String,
    val colonia: String,
    val imagen: String?,
    val fecha: String
)

data class ReportsResponse(
    val reportes: List<ReportResponse>
)

/**
 * Enum que representa los tipos de reportes disponibles en el sistema.
 * Cada valor tiene un nombre de visualización que se muestra en la interfaz de usuario.
 */
enum class ReportType(val displayName: String) {
    ALUMBRADO_PUBLICO("ALUMBRADO PÚBLICO"),
    ANIMALES_CALLEJEROS("ANIMALES CALLEJEROS O EN SITUACIÓN DE ABANDONO"),
    BACHES("BACHES"),
    BASURA("BASURA O ESCOMBRO"),
    FUMIGACION("FUMIGACIÓN O PLAGAS"),
    FUGAS("FUGAS O DRENAJE"),
    OTRO("OTRO ASUNTO");

    companion object {
        /**
         * Obtiene el valor del enum a partir de su nombre de visualización.
         * Si no se encuentra, retorna OTRO como valor por defecto.
         *
         * @param displayName El nombre de visualización del tipo de reporte
         * @return El valor del enum correspondiente
         */
        fun fromDisplayName(displayName: String): ReportType {
            return values().find { it.displayName == displayName } ?: OTRO
        }
    }
} 