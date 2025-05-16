package mx.itson.reporteciudadanopf.api

import mx.itson.reporteciudadanopf.models.Report
import mx.itson.reporteciudadanopf.models.ReportResponse
import mx.itson.reporteciudadanopf.models.ReportsResponse
import retrofit2.Response
import retrofit2.http.*

interface ReportApi {
    @POST("reporte.php")
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    suspend fun createReport(
        @Header("Authorization") token: String,
        @Body report: Report
    ): Response<ReportResponse>

    @GET("reportes.php")
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    suspend fun getReports(
        @Header("Authorization") token: String
    ): Response<ReportsResponse>
} 