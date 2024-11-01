package screen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import viewmodel.AuthViewModel

@Composable
fun NavigationGraph (navController: NavHostController,
                     authViewModel: AuthViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ){
        composable(Screen.LoginScreen.route) {
            SignIn (
                authViewModel = authViewModel,
                onNavigatetoSignup = {navController.navigate(Screen.signUp.route)}
            ){
                navController.navigate(Screen.ChatRoomsScreen.route)
            }

        }
        composable(Screen.signUp.route) {
            SignUpScreen (
                authViewModel = authViewModel,
                onNavigateToLogin = {navController.navigate(Screen.LoginScreen.route)}
            )
        }
        composable(Screen.ChatRoomsScreen.route) {
            ChatRoomListScreen{
                navController.navigate("${Screen.ChatScreen.route}/${it.id}")
            }
        }
        composable("${Screen.ChatScreen.route}/{roomId}") {
            val roomId: String = it
                .arguments?.getString("roomId")?:""
            ChatScreen(roomId = roomId)
        }
    }
}