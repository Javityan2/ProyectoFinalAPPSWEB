package mx.itson.reporteciudadanopf.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.itson.reporteciudadanopf.models.Report
import mx.itson.reporteciudadanopf.api.ReportApi
import mx.itson.reporteciudadanopf.api.RetrofitClient
import mx.itson.reporteciudadanopf.utils.ImageUtils
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed class ReportUiState {
    object Initial : ReportUiState()
    object Loading : ReportUiState()
    object Success : ReportUiState()
    data class Error(val message: String) : ReportUiState()
}

class ReportViewModel(private val context: Context) : ViewModel() {
    var uiState by mutableStateOf<ReportUiState>(ReportUiState.Initial)
        private set

    private val api = RetrofitClient.reportApi

    fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir("Images")
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    fun getUriForFile(file: File): Uri {
        return Uri.fromFile(file)
    }

    fun submitReport(report: Report) {
        viewModelScope.launch {
            uiState = ReportUiState.Loading
            try {
                val reportWithImage = if (report.imagen != null) {
                    val imageUri = Uri.parse(report.imagen)
                    val base64Image = ImageUtils.uriToBase64(context, imageUri)
                    report.copy(imagen = base64Image)
                } else {
                    report
                }

                val response = api.createReport(
                    token = "Bearer a0f4dcad-5903-482f-8982-88ec8bc6156e",
                    report = reportWithImage
                )
                
                if (response.isSuccessful) {
                    uiState = ReportUiState.Success
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                    uiState = ReportUiState.Error("Error al enviar reporte: ${response.code()} - $errorMessage")
                }
            } catch (e: Exception) {
                uiState = ReportUiState.Error(e.message ?: "Error al enviar el reporte")
            }
        }
    }
}

class ReportViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReportViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReportViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 