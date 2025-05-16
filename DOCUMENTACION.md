# Documentación Técnica - Reporte Ciudadano PF

## Estructura del Proyecto

### Pantallas (Screens)
Todas las pantallas están en `app/src/main/java/mx/itson/reporteciudadanopf/ui/screens/`:

1. **SplashScreen.kt**
   - Es la primera pantalla que ve el usuario
   - Usa `LaunchedEffect` para el temporizador de 2 segundos
   - Tiene animaciones de fade y scale para una entrada suave
   - Navega automáticamente al menú principal

2. **MenuScreen.kt**
   - Pantalla principal con las opciones de la app
   - Usa `Card` para cada opción del menú
   - Tiene animaciones de escala al cargar
   - Maneja la navegación a ReportScreen y ContactScreen

3. **ReportScreen.kt**
   - Pantalla más compleja de la app
   - Usa `Scaffold` para la estructura básica
   - Divide la información en secciones con `Card`
   - Maneja la captura y procesamiento de imágenes
   - Valida todos los campos en tiempo real

4. **ContactScreen.kt**
   - Muestra la información de contacto
   - Usa `Card` para cada tipo de información
   - Incluye iconos para mejor visualización

### ViewModels
Están en `app/src/main/java/mx/itson/reporteciudadanopf/viewmodels/`:

1. **ReportViewModel.kt**
   - Maneja toda la lógica de negocio
   - Procesa las imágenes a base64
   - Hace las llamadas a la API
   - Maneja los estados de la UI (loading, error, success)

### Modelos
En `app/src/main/java/mx/itson/reporteciudadanopf/models/`:

1. **Report.kt**
   - Define la estructura de un reporte
   - Incluye todos los campos necesarios
   - Usa data class para mejor manejo

2. **Colonias.kt**
   - Enum con todas las colonias disponibles
   - Incluye displayName para mostrar nombres amigables

### Utilidades
En `app/src/main/java/mx/itson/reporteciudadanopf/utils/`:

1. **ImageUtils.kt**
   - Maneja la conversión de imágenes a base64
   - Optimiza las imágenes antes de enviarlas
   - Maneja los errores de procesamiento

### Navegación
En `app/src/main/java/mx/itson/reporteciudadanopf/Navigation/`:

1. **AppNavigation.kt**
   - Define todas las rutas de la app
   - Maneja la navegación entre pantallas
   - Configura el token para las llamadas a la API

## Flujo de Datos

### Creación de Reporte
1. El usuario llena el formulario en `ReportScreen`
2. `ReportViewModel` valida los datos en tiempo real
3. Si hay una imagen:
   - Se procesa con `ImageUtils`
   - Se convierte a base64
   - Se optimiza el tamaño
4. Se crea el objeto `Report`
5. Se envía al servidor usando Retrofit
6. Se muestra el resultado al usuario

### Manejo de Imágenes
1. El usuario selecciona una imagen (cámara o galería)
2. Se crea un archivo temporal
3. Se muestra la vista previa
4. Al enviar:
   - Se comprime la imagen
   - Se convierte a JPEG
   - Se transforma a base64
   - Se agrega el prefijo requerido

## API y Servicios

### Endpoints
- Base URL: `https://reporteciudadano.itson.mx/api`
- Reportes: `/reportes` (POST)

### Formato de Datos
```json
{
  "nombre_interesado": "string",
  "direccion": "string",
  "colonia": "string",
  "celular": "string",
  "correo": "string",
  "tipo": "string",
  "descripcion": "string",
  "imagen": "string (base64)"
}
```

## Manejo de Errores

### Errores Comunes
1. **Sin conexión a internet**
   - Muestra mensaje amigable
   - Sugiere verificar la conexión

2. **Error en la API**
   - Muestra el mensaje del servidor
   - Permite reintentar el envío

3. **Error en imágenes**
   - Si la imagen es muy grande
   - Si hay problemas de permisos
   - Si falla la conversión

### Validaciones
- Todos los campos obligatorios
- Formato de correo válido
- Celular de 10 dígitos
- Descripción mínima de 10 caracteres

## Permisos

### Android 13+
- `CAMERA`: Para tomar fotos
- `READ_MEDIA_IMAGES`: Para galería

### Android 12 y anteriores
- `CAMERA`: Para tomar fotos
- `READ_EXTERNAL_STORAGE`: Para galería

## Tecnologías y Librerías

### Principales
- Kotlin 1.9.0
- Jetpack Compose 1.5.0
- Material Design 3
- Coil 2.4.0
- Retrofit 2.9.0
- Coroutines 1.7.3

### Dependencias
```gradle
dependencies {
    // Compose
    implementation "androidx.compose.ui:ui:1.5.0"
    implementation "androidx.compose.material3:material3:1.1.0"
    
    // Coil para imágenes
    implementation "io.coil-kt:coil-compose:2.4.0"
    
    // Retrofit para API
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation "com.squareup.retrofit2:converter-gson:2.9.0"
    
    // Coroutines
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"
}
```

## Buenas Prácticas Implementadas

1. **UI/UX**
   - Diseño limpio y moderno
   - Feedback visual inmediato
   - Validaciones en tiempo real
   - Mensajes de error claros

2. **Código**
   - Arquitectura MVVM
   - Separación de responsabilidades
   - Manejo de estados con StateFlow
   - Coroutines para operaciones asíncronas

3. **Rendimiento**
   - Optimización de imágenes
   - Carga lazy de componentes
   - Manejo eficiente de memoria

4. **Seguridad**
   - Validación de datos
   - Manejo seguro de permisos
   - Comunicación HTTPS 

## Notas de Implementación

### 1. Manejo de Imágenes
- **Captura de Fotos**:
  - Se utiliza `FileProvider` para generar URIs seguras
  - Las imágenes se almacenan temporalmente en el directorio de la app
  - Se implementa limpieza automática de archivos temporales
  - Se manejan permisos de cámara y galería según la versión de Android

- **Procesamiento de Imágenes**:
  - Tamaño máximo: 640x640 píxeles
  - Formato: PNG para mantener calidad
  - Compresión: Sin pérdida de calidad
  - Conversión a base64 para envío al servidor

### 2. Validación de Datos
- **Campos Requeridos**:
  - Nombre: mínimo 3 caracteres
  - Celular: exactamente 10 dígitos
  - Correo: formato válido de email
  - Dirección: no vacía
  - Colonia: selección obligatoria
  - Tipo de reporte: selección obligatoria
  - Descripción: mínimo 10 caracteres

- **Validación en Tiempo Real**:
  - Se utiliza `LaunchedEffect` para monitorear cambios
  - Feedback visual inmediato al usuario
  - Mensajes de error descriptivos
  - Deshabilitación del botón de envío si hay errores

### 3. Comunicación con el Servidor
- **Configuración de API**:
  - URL Base: https://mcaconsultores.com.mx/apireporte/
  - Token de autenticación: Bearer
  - Timeout: 30 segundos
  - Logging detallado en desarrollo

- **Manejo de Errores**:
  - Reconexión automática
  - Mensajes de error amigables
  - Logging de errores para depuración
  - Manejo de estados de carga

### 4. Navegación
- **Flujo de Pantallas**:
  - Splash (2 segundos) -> Menu
  - Menu -> Report/Contact
  - Report/Contact -> Menu (regreso)
  - Limpieza de stack al salir de Splash

- **Estado de Navegación**:
  - Preservación de estado en ReportScreen
  - Limpieza de estado al regresar
  - Manejo de configuración de pantalla

### 5. UI/UX
- **Diseño Material 3**:
  - Tema personalizado
  - Colores dinámicos
  - Modo oscuro/claro
  - Tipografía consistente

- **Componentes Reutilizables**:
  - Cards para secciones
  - Campos de texto estilizados
  - Botones con estados
  - Diálogos de feedback

### 6. Permisos
- **Android 13+**:
  - `CAMERA`: Para tomar fotos
  - `READ_MEDIA_IMAGES`: Para galería

- **Android 12 y anteriores**:
  - `CAMERA`: Para tomar fotos
  - `READ_EXTERNAL_STORAGE`: Para galería

### 7. Optimizaciones
- **Rendimiento**:
  - Lazy loading de imágenes
  - Compresión de imágenes
  - Manejo eficiente de memoria
  - Limpieza de recursos

- **Experiencia de Usuario**:
  - Feedback inmediato
  - Estados de carga
  - Manejo de errores
  - Persistencia de datos


