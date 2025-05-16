package mx.itson.reporteciudadanopf.api

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.itson.reporteciudadanopf.models.Report
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Cliente para interactuar con la API de reportes ciudadanos.
 * Utiliza Retrofit y OkHttp para realizar las llamadas HTTP.
 * Maneja la autenticación y el procesamiento de respuestas.
 *
 * @property context Contexto de la aplicación
 */
class ReportApiClient(private val context: Context) {
    private val TAG = "ReportApiClient"
    private val BASE_URL = "https://mcaconsultores.com.mx/apireporte/"
    private val AUTH_TOKEN = "a0f4dcad-5903-482f-8982-88ec8bc6156e"

    /**
     * Interceptor para logging de las llamadas HTTP.
     * Registra el cuerpo de las peticiones y respuestas para depuración.
     */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * Cliente HTTP configurado con:
     * - Interceptor de logging
     * - Headers de autenticación
     * - Timeouts de conexión
     * - Manejo de errores
     */
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer $AUTH_TOKEN")
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    /**
     * Instancia de Retrofit configurada con:
     * - URL base de la API
     * - Cliente HTTP personalizado
     * - Convertidor Gson para JSON
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * Interfaz de la API generada por Retrofit.
     * Contiene los métodos para interactuar con el servidor.
     */
    val reportApi: ReportApi = retrofit.create(ReportApi::class.java)

    /**
     * Envía un reporte al servidor.
     * Maneja la conversión de la imagen a base64 si existe.
     *
     * @param report Reporte a enviar
     * @return Result<Unit> Resultado de la operación
     */
    suspend fun sendReport(report: Report): Result<Unit> {
        return try {
            val response = reportApi.sendReport(report)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al enviar el reporte: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al enviar reporte", e)
            Result.failure(e)
        }
    }
} 