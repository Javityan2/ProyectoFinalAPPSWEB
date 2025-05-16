package mx.itson.reporteciudadanopf.api

import mx.itson.reporteciudadanopf.models.Report
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz que define los endpoints de la API para el manejo de reportes.
 * Utiliza Retrofit para realizar las llamadas HTTP.
 */
interface ReportService {
    /**
     * Envía un reporte al servidor.
     *
     * @param report El objeto Report que contiene los datos del reporte
     * @return Una Response que indica si la operación fue exitosa
     */
    @POST("reporte.php")
    suspend fun sendReport(@Body report: Report): Response<Unit>
} 