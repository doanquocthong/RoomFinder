package com.example.projectandroidapp_findingroom.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectandroidapp_findingroom.R
import com.example.projectandroidapp_findingroom.ui.theme.fontFamily

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ){
        HeaderHomeScreen()
        LazyColumn (modifier = Modifier.fillMaxHeight()){
            items(30) {
                CardRoom()
            }
        }
    }
}

@Composable
fun HeaderHomeScreen() {
    Surface(
        color = colorResource(R.color.main_color),
        modifier = Modifier
            .height(140.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = "Tìm kiếm",
            onValueChange = {},
            textStyle = TextStyle(
                fontFamily = fontFamily,
                color = Color.Gray,
                fontSize = 14.sp
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .padding(top = 60.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
        )
    }
}

@Preview (showBackground = true)
@Composable
fun CardRoom() {
    val address = "597/14 Quang Trung, Phường 11, Gò Vấp, Hồ Chí Minh"
    val price = "5 triệu"
    val state = true
    Surface(
        shape = RoundedCornerShape(17.dp),
        shadowElevation = 10.dp,
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 30.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.anh1),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.padding(5.dp))
            Column (
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(
                    "Địa chỉ: ${address}",
                    fontFamily = fontFamily
                )
                Spacer(Modifier.padding(5.dp))
                Text(
                    "Giá: ${price}",
                    color = Color.Blue,
                    fontFamily = fontFamily

                )
                Spacer(Modifier.padding(5.dp))
                Surface (
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.LightGray,
                    shape = RoundedCornerShape(17.dp)
                ) {
                    Text(
                        if (state) {
                            "Còn trống 1 phòng"
                        }else "Phòng đã có người cọc",
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontFamily = fontFamily

                    )
                }
            }
        }
    }
}