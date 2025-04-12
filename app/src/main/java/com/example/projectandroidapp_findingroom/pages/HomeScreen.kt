package com.example.projectandroidapp_findingroom.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.projectandroidapp_findingroom.R


@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.main_color)),
        contentAlignment = Alignment.Center
    ){
        Text(
            "HomeScreen",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
    }
}