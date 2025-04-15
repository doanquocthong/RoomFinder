package com.example.projectandroidapp_findingroom

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.projectandroidapp_findingroom.ui.theme.fontFamily
import com.example.projectandroidapp_findingroom.viewmodel.AuthState
import com.example.projectandroidapp_findingroom.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, authViewModel: AuthViewModel) {
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login")
            is AuthState.Authenticated -> navController.navigate("main")
            else -> Unit
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.main_color)),
        contentAlignment = Alignment.Center,
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(R.drawable.home_img),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
            )
            Text(
                "Tìm nhà trọ",
                modifier = Modifier.padding(top = 15.dp),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontFamily = fontFamily
                )
        }

    }
}