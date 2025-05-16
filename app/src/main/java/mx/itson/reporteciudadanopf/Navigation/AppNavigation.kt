package mx.itson.reporteciudadanopf.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import mx.itson.reporteciudadanopf.ui.screens.MenuScreen
import mx.itson.reporteciudadanopf.ui.screens.ReportScreen
import mx.itson.reporteciudadanopf.ui.screens.SplashScreen
import mx.itson.reporteciudadanopf.ui.screens.ContactScreen

/**
 * Definición de las rutas de navegación de la aplicación.
 * Cada ruta representa una pantalla en la app.
 */
sealed class Screen(val route: String) {
    /** Pantalla de inicio que se muestra por 2 segundos */
    object Splash : Screen("splash")
    
    /** Menú principal con las opciones de la app */
    object Menu : Screen("menu")
    
    /** Pantalla para crear un nuevo reporte */
    object Report : Screen("report")
    
    /** Pantalla de información de contacto */
    object Contact : Screen("contact")
}

/**
 * Navegación principal de la aplicación.
 * Define las rutas y la navegación entre pantallas.
 * 
 * Flujo de navegación:
 * 1. Splash -> Menu (automático después de 2 segundos)
 * 2. Menu -> Report (al seleccionar "Nuevo Reporte")
 * 3. Menu -> Contact (al seleccionar "Contacto")
 * 4. Report -> Menu (al enviar reporte o regresar)
 * 5. Contact -> Menu (al regresar)
 *
 * @param navController Controlador de navegación que maneja las transiciones
 */
@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // Pantalla de inicio
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Screen.Menu.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // Menú principal
        composable(Screen.Menu.route) {
            MenuScreen(
                onNavigateToReport = {
                    navController.navigate(Screen.Report.route)
                },
                onNavigateToContact = {
                    navController.navigate(Screen.Contact.route)
                }
            )
        }

        // Pantalla de reporte
        composable(Screen.Report.route) {
            ReportScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla de contacto
        composable(Screen.Contact.route) {
            ContactScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
} 