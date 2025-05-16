package mx.itson.reporteciudadanopf.ui.screens

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import mx.itson.reporteciudadanopf.models.Colonias
import mx.itson.reporteciudadanopf.models.Report
import mx.itson.reporteciudadanopf.models.ReportType
import mx.itson.reporteciudadanopf.utils.ImageUtils
import mx.itson.reporteciudadanopf.viewmodels.ReportViewModel
import mx.itson.reporteciudadanopf.viewmodels.ReportViewModelFactory
import mx.itson.reporteciudadanopf.viewmodels.ReportUiState
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.animation.animateContentSize

/**
 * Pantalla principal para crear y enviar reportes ciudadanos.
 * Permite al usuario ingresar información del reporte, seleccionar una ubicación,
 * tomar una foto y enviar el reporte al servidor.
 *
 * Características principales:
 * - Validación en tiempo real de todos los campos
 * - Captura de fotos desde cámara o galería
 * - Selección de colonia y tipo de reporte
 * - Manejo de errores y estados de carga
 * - Interfaz intuitiva y amigable
 *
 * @param onNavigateBack Función para navegar hacia atrás
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: ReportViewModel = viewModel(
        factory = ReportViewModelFactory(context)
    )
    val uiState = viewModel.uiState
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var showImageOptions by remember { mutableStateOf(false) }
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedReportType by remember { mutableStateOf<ReportType?>(null) }
    var selectedColonia by remember { mutableStateOf<String?>(null) }
    var nombre by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    // Estados de validación
    var nombreError by remember { mutableStateOf<String?>(null) }
    var direccionError by remember { mutableStateOf<String?>(null) }
    var celularError by remember { mutableStateOf<String?>(null) }
    var correoError by remember { mutableStateOf<String?>(null) }
    var descripcionError by remember { mutableStateOf<String?>(null) }

    // Validación en tiempo real
    LaunchedEffect(nombre) {
        nombreError = when {
            nombre.isBlank() -> "El nombre es requerido"
            nombre.length < 3 -> "El nombre debe tener al menos 3 caracteres"
            else -> null
        }
    }

    LaunchedEffect(direccion) {
        direccionError = when {
            direccion.isBlank() -> "La dirección es requerida"
            else -> null
        }
    }

    LaunchedEffect(celular) {
        celularError = when {
            celular.isBlank() -> "El celular es requerido"
            !celular.matches(Regex("^[0-9]{10}$")) -> "Ingrese un número válido de 10 dígitos"
            else -> null
        }
    }

    LaunchedEffect(correo) {
        correoError = when {
            correo.isBlank() -> "El correo es requerido"
            !correo.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)\$")) -> "Ingrese un correo válido"
            else -> null
        }
    }

    LaunchedEffect(descripcion) {
        descripcionError = when {
            descripcion.isBlank() -> "La descripción es requerida"
            descripcion.length < 10 -> "La descripción debe tener al menos 10 caracteres"
            else -> null
        }
    }

    /**
     * Crea un archivo temporal para almacenar la imagen capturada.
     * El nombre del archivo incluye una marca de tiempo para evitar colisiones.
     *
     * @return File Archivo temporal para la imagen
     */
    fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        storageDir?.mkdirs() // Asegurar que el directorio existe
        return File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
    }

    /**
     * Obtiene la URI de un archivo para compartirlo con otras aplicaciones.
     * Utiliza FileProvider para generar una URI segura.
     *
     * @param file El archivo para el cual se generará la URI
     * @return Uri La URI del archivo
     */
    fun getUriForFile(file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            tempImageUri?.let { uri ->
                selectedImageUri = uri
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            selectedImageUri = it
        }
    }

    // Launcher para solicitar permisos de cámara
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            tempImageUri?.let { uri ->
                cameraLauncher.launch(uri)
            }
        } else {
            errorMessage = "Se requiere permiso para acceder a la cámara"
            showErrorDialog = true
        }
    }

    // Launcher para solicitar permisos de galería
    val galleryPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        } else {
            errorMessage = "Se requiere permiso para acceder a la galería"
            showErrorDialog = true
        }
    }

    LaunchedEffect(Unit) {
        try {
            if (tempImageUri == null) {
                val file = createImageFile()
                tempImageUri = getUriForFile(file)
            }
        } catch (e: Exception) {
            errorMessage = "Error al crear el archivo de imagen: ${e.message}"
            showErrorDialog = true
        }
    }

    val focusManager = LocalFocusManager.current

    val isLoading = uiState is ReportUiState.Loading

    LaunchedEffect(uiState) {
        when (uiState) {
            is ReportUiState.Success -> {
                showSuccessDialog = true
            }
            is ReportUiState.Error -> {
                showErrorDialog = true
                errorMessage = (uiState as? ReportUiState.Error)?.message ?: "Error desconocido"
            }
            else -> {}
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Éxito") },
            text = { Text("El reporte se ha enviado correctamente") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSuccessDialog = false
                        onNavigateBack()
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(
                    onClick = { showErrorDialog = false }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Reporte") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Sección de información personal
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Información Personal",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre completo") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = nombreError != null,
                        supportingText = { nombreError?.let { Text(it) } },
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        }
                    )

                    OutlinedTextField(
                        value = celular,
                        onValueChange = { if (it.length <= 10) celular = it },
                        label = { Text("Número de celular") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = celularError != null,
                        supportingText = { celularError?.let { Text(it) } },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null)
                        }
                    )

                    OutlinedTextField(
                        value = correo,
                        onValueChange = { correo = it },
                        label = { Text("Correo electrónico") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = correoError != null,
                        supportingText = { correoError?.let { Text(it) } },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null)
                        }
                    )
                }
            }

            // Sección de ubicación
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Ubicación del Reporte",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    OutlinedTextField(
                        value = direccion,
                        onValueChange = { direccion = it },
                        label = { Text("Dirección") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = direccionError != null,
                        supportingText = { direccionError?.let { Text(it) } },
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.LocationOn, contentDescription = null)
                        }
                    )

                    var expandedColonia by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expandedColonia,
                        onExpandedChange = { expandedColonia = it }
                    ) {
                        OutlinedTextField(
                            value = selectedColonia ?: "",
                            onValueChange = { },
                            readOnly = true,
                            label = { Text("Colonia") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedColonia) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            leadingIcon = {
                                Icon(Icons.Default.Map, contentDescription = null)
                            }
                        )

                        ExposedDropdownMenu(
                            expanded = expandedColonia,
                            onDismissRequest = { expandedColonia = false }
                        ) {
                            Colonias.values().forEach { coloniaItem ->
                                DropdownMenuItem(
                                    text = { Text(coloniaItem.displayName) },
                                    onClick = {
                                        selectedColonia = coloniaItem.displayName
                                        expandedColonia = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Sección de detalles del reporte
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Detalles del Reporte",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    var expandedTipo by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expandedTipo,
                        onExpandedChange = { expandedTipo = it }
                    ) {
                        OutlinedTextField(
                            value = selectedReportType?.displayName ?: "",
                            onValueChange = { },
                            readOnly = true,
                            label = { Text("Tipo de Reporte") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTipo) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            leadingIcon = {
                                Icon(Icons.Default.Category, contentDescription = null)
                            }
                        )

                        ExposedDropdownMenu(
                            expanded = expandedTipo,
                            onDismissRequest = { expandedTipo = false }
                        ) {
                            ReportType.values().forEach { tipo ->
                                DropdownMenuItem(
                                    text = { Text(tipo.displayName) },
                                    onClick = {
                                        selectedReportType = tipo
                                        expandedTipo = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripción del problema") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = descripcionError != null,
                        supportingText = { descripcionError?.let { Text(it) } },
                        minLines = 3,
                        leadingIcon = {
                            Icon(Icons.Default.Description, contentDescription = null)
                        }
                    )
                }
            }

            // Sección de imagen
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Evidencia Fotográfica (Opcional)",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Opcional",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    Text(
                        text = "Puedes agregar una foto que muestre claramente el problema. Esto ayudará a una mejor atención de tu reporte.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Tomar foto"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Tomar Foto")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                                    galleryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                                } else {
                                    galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoLibrary,
                                contentDescription = "Seleccionar de galería"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Galería")
                        }
                    }

                    selectedImageUri?.let { uri ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(vertical = 8.dp)
                                .animateContentSize(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Image(
                                    painter = rememberAsyncImagePainter(uri),
                                    contentDescription = "Vista previa de la imagen",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                IconButton(
                                    onClick = { 
                                        selectedImageUri = null
                                        try {
                                            val file = createImageFile()
                                            tempImageUri = getUriForFile(file)
                                        } catch (e: Exception) {
                                            errorMessage = "Error al crear el archivo de imagen: ${e.message}"
                                            showErrorDialog = true
                                        }
                                    },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar foto",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Botón de envío
            Button(
                onClick = {
                    val report = Report(
                        nombre_interesado = nombre,
                        direccion = direccion,
                        colonia = selectedColonia ?: "",
                        celular = celular,
                        correo = correo,
                        tipo = selectedReportType?.displayName ?: "",
                        descripcion = descripcion,
                        imagen = selectedImageUri?.toString()
                    )
                    viewModel.submitReport(report)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !isLoading && nombreError == null && direccionError == null && 
                         celularError == null && correoError == null && descripcionError == null &&
                         selectedColonia != null && selectedReportType != null
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Enviar Reporte")
                }
            }
        }
    }
}

private fun validateForm(
    nombre: String,
    direccion: String,
    colonia: String?,
    celular: String,
    correo: String,
    tipo: ReportType?,
    descripcion: String
): Boolean {
    if (nombre.isBlank()) return false
    if (direccion.isBlank()) return false
    if (colonia.isNullOrBlank()) return false
    if (celular.isBlank()) return false
    if (correo.isBlank()) return false
    if (tipo == null) return false
    if (descripcion.isBlank()) return false
    return true
} 