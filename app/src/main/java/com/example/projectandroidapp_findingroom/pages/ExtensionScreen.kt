package com.example.projectandroidapp_findingroom.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectandroidapp_findingroom.R
import com.example.projectandroidapp_findingroom.ui.theme.fontFamily

@Composable
fun ExtensionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.main_color))
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        HeaderUI()
        Box(
            modifier = Modifier
                .padding(top = 30.dp, bottom = 60.dp)
        ){
            FindRoomUI()
        }
        AddRoomUI()
    }
}

@Composable
fun HeaderUI() {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.home_img),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
        )
        Text(
            text = "Tìm Nhà Trọ",
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp),
            fontSize = 25.sp,
            fontFamily = fontFamily
        )
    }

}

@Composable
fun FindRoomUI() {
    Surface(
        shape = RoundedCornerShape(30.dp),
        color = Color.White,
        modifier = Modifier
            .height(170.dp)
            .width(170.dp)
            .padding(),
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Tìm phòng",
                modifier = Modifier
                    .padding(),
                fontSize = 20.sp,
                fontFamily = fontFamily
            )
            Image(
                painter = painterResource(R.drawable.baseline_search_24),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
            )
        }

    }
}

@Composable
fun AddRoomUI() {
    Surface(
        shape = RoundedCornerShape(30.dp),
        color = Color.White,
        modifier = Modifier
            .height(170.dp)
            .width(170.dp),
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Thêm phòng",
                modifier = Modifier
                    .padding(),
                fontSize = 20.sp,
                fontFamily = fontFamily
            )
            Image(
                painter = painterResource(R.drawable.baseline_add_home_24),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
            )
        }

    }
}


