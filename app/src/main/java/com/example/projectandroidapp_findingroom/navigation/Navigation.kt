package com.example.projectandroidapp_findingroom.navigation


import EditRoom
import android.annotation.SuppressLint
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firestore.RoomViewModel
import com.example.projectandroidapp_findingroom.AddRoomScreen
import com.example.projectandroidapp_findingroom.MainScreen
import com.example.projectandroidapp_findingroom.Room
import com.example.projectandroidapp_findingroom.SplashScreen
import com.example.projectandroidapp_findingroom.authetication.LoginScreen
import com.example.projectandroidapp_findingroom.authetication.RegisterScreen
import com.example.projectandroidapp_findingroom.pages.DetailRoom
import com.example.projectandroidapp_findingroom.pages.EditRoomUI
import com.example.projectandroidapp_findingroom.pages.EditScreen
import com.example.projectandroidapp_findingroom.pages.ExtensionScreen
import com.example.projectandroidapp_findingroom.viewmodel.AuthViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Navigation() {
    val modifier = Modifier
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val roomViewModel: RoomViewModel = viewModel()
    NavHost(navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController, authViewModel) }
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("register") { RegisterScreen(navController, authViewModel) }
        composable("main") { MainScreen(modifier, navController, authViewModel, roomViewModel) }
        composable("add") { AddRoomScreen(roomViewModel, navController, authViewModel) }
        composable("detail/{roomId}") { backStackEntry ->
            val roomId = backStackEntry.arguments?.getString("roomId") ?: ""
            DetailRoom(roomId = roomId, roomViewModel = roomViewModel, navController = navController)
        }
        composable("edit/{roomId}") { backStackEntry ->
            val roomId = backStackEntry.arguments?.getString("roomId") ?: ""
            EditRoom(roomId = roomId, roomViewModel = roomViewModel, navController = navController, authViewModel)
        }
        composable("author/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            EditScreen(name = name, roomViewModel = roomViewModel, navController = navController, authViewModel = authViewModel)
        }

    }
}