# Reporte Ciudadano PF

App móvil para reportar problemas en la ciudad de manera rápida y sencilla.

## ¿Cómo funciona la app?

### 1. Pantalla de Inicio (Splash Screen)
- Es lo primero que verás al abrir la app
- Muestra el logo y el nombre de la app
- Dura 2 segundos y te lleva automáticamente al menú principal
- Tiene una animación suave de entrada

### 2. Menú Principal
- Es la pantalla central de la app
- Tiene dos opciones principales:
  - **Nuevo Reporte**: Para crear un reporte de algún problema
  - **Contacto**: Para ver la información de contacto
- Diseño limpio y fácil de usar

### 3. Crear Reporte
Esta es la parte más importante de la app. Aquí podrás:
- Llenar tu información personal:
  - Nombre completo
  - Número de celular
  - Correo electrónico
- Especificar la ubicación:
  - Dirección exacta
  - Seleccionar la colonia de una lista
- Detallar el problema:
  - Elegir el tipo de reporte (bache, alumbrado, etc.)
  - Escribir una descripción detallada
- Agregar una foto (opcional):
  - Puedes tomar una foto con la cámara
  - O seleccionar una de tu galería

### 4. Envío del Reporte
- Cuando presionas "Enviar Reporte":
  1. La app valida que todos los campos estén correctos:
     - Nombre: No puede estar vacío y debe tener al menos 3 caracteres
     - Celular: Debe tener 10 dígitos y ser un número válido
     - Correo: Debe tener formato válido (ejemplo@dominio.com)
     - Dirección: No puede estar vacía
     - Colonia: Debe seleccionarse una de la lista
     - Tipo de reporte: Debe seleccionarse uno de la lista
     - Descripción: Debe tener al menos 10 caracteres
  2. Si hay una foto, la convierte a formato base64
  3. Envía toda la información al servidor usando una API REST
  4. El servidor guarda el reporte en una base de datos
  5. Te muestra un mensaje de éxito o error

### 5. Pantalla de Contacto
- Muestra la información de contacto del departamento
- Incluye:
  - Número telefónico
  - Correo electrónico
  - Dirección física

## ¿Cómo se conecta con el servidor?

La app usa una API REST para comunicarse con el servidor:
- URL base: `https://reporteciudadano.itson.mx/api`
- Endpoint para reportes: `/reportes`
- Método: POST
- Formato de datos: JSON

### Ejemplo de cómo se envía un reporte:
```json
{
  "nombre_interesado": "Juan Pérez",
  "direccion": "Calle Principal 123",
  "colonia": "Centro",
  "celular": "6441234567",
  "correo": "juan@email.com",
  "tipo": "Bache",
  "descripcion": "Bache grande en la esquina",
  "imagen": "data:image/jpeg;base64,..." // Si se incluye una foto
}
```

## Tecnologías usadas
- Kotlin
- Jetpack Compose
- Material Design 3
- Coil (para cargar imágenes)
- Retrofit (para las llamadas a la API)
- Coroutines (para operaciones asíncronas)

## Manejo de Imágenes
La app maneja las imágenes de la siguiente manera:

### Permisos Requeridos
La app necesita permisos para acceder a la cámara y galería:

#### Permisos de Cámara
- **Android 13 y superior**:
  - `android.permission.CAMERA`: Para tomar fotos
  - La app te pedirá este permiso la primera vez que intentes usar la cámara
  - Puedes negar el permiso, pero no podrás tomar fotos
  - Puedes cambiar el permiso en cualquier momento en Ajustes > Apps > Reporte Ciudadano > Permisos

#### Permisos de Galería
- **Android 13 y superior**:
  - `android.permission.READ_MEDIA_IMAGES`: Para seleccionar fotos de la galería
- **Android 12 y anteriores**:
  - `android.permission.READ_EXTERNAL_STORAGE`: Para acceder a la galería
- La app te pedirá estos permisos la primera vez que intentes seleccionar una foto
- Puedes negar el permiso, pero no podrás seleccionar fotos de la galería
- Puedes cambiar el permiso en cualquier momento en Ajustes > Apps > Reporte Ciudadano > Permisos

### ¿Qué pasa si niego los permisos?
- La app seguirá funcionando normalmente
- No podrás tomar fotos ni seleccionar imágenes de la galería
- Podrás seguir enviando reportes sin fotos
- En cualquier momento puedes:
  1. Ir a Ajustes del teléfono
  2. Buscar "Reporte Ciudadano"
  3. Activar los permisos que necesites

### Captura de Fotos
- **Cámara**: 
  - Solicita permiso para usar la cámara
  - Crea un archivo temporal para guardar la foto
  - Muestra una vista previa antes de guardar
  - Permite tomar otra foto si no te gusta la primera

- **Galería**:
  - Solicita permiso para acceder a la galería
  - Permite seleccionar cualquier imagen
  - Muestra una vista previa de la imagen seleccionada

### Procesamiento de Imágenes
1. **Optimización**:
   - Comprime la imagen para reducir el tamaño
   - Mantiene una calidad aceptable para identificar el problema
   - Ajusta el tamaño máximo a 1024x1024 píxeles

2. **Conversión**:
   - Convierte la imagen a formato JPEG
   - La transforma a base64 para enviarla al servidor
   - Agrega el prefijo `data:image/jpeg;base64,` requerido por la API

3. **Manejo de Errores**:
   - Si la imagen es muy grande, muestra un mensaje de error
   - Si hay problemas de permisos, te guía para activarlos
   - Si falla la conversión, te permite intentar con otra imagen
   