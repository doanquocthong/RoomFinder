package com.example.projectandroidapp_findingroom.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projectandroidapp_findingroom.MainScreen
import com.example.projectandroidapp_findingroom.SplashScreen
import com.example.projectandroidapp_findingroom.authetication.LoginScreen
import com.example.projectandroidapp_findingroom.authetication.RegisterScreen
import com.example.projectandroidapp_findingroom.pages.ExtensionScreen
import com.example.projectandroidapp_findingroom.viewmodel.AuthViewModel

@Composable
fun Navigation() {
    val modifier = Modifier
    val navController = rememberNavController()
    val authViewModel : AuthViewModel = viewModel()
    NavHost(navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController, authViewModel) }
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("register") { RegisterScreen(navController, authViewModel) }
        composable("main") { MainScreen(modifier, navController, authViewModel) }
    }
}