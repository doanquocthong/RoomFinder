package com.example.projectandroidapp_findingroom.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projectandroidapp_findingroom.MainScreen
import com.example.projectandroidapp_findingroom.SplashScreen
import com.example.projectandroidapp_findingroom.authetication.LoginScreen
import com.example.projectandroidapp_findingroom.authetication.RegisterScreen
import com.example.projectandroidapp_findingroom.pages.ExtensionScreen

@Composable
fun Navigation() {
    val modifier = Modifier
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main") {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("main") { MainScreen(modifier, navController) }
    }
}