package com.example.projectandroidapp_findingroom.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.projectandroidapp_findingroom.R

@Composable
fun AccountScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.main_color)),
        contentAlignment = Alignment.Center
    ){
        Column {
            Text(
                "AccountScreen",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = {
                    navController.navigate("login")
                }
            ) {
                Text(
                    "Go to login page"
                )
            }
        }

    }
}