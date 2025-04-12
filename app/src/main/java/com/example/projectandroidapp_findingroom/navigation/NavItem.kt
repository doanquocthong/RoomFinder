package com.example.projectandroidapp_findingroom.navigation
import androidx.compose.ui.graphics.painter.Painter

data class NavItem(
    val label : String,
    val icon : Painter,
    val badgeCount : Int,
)