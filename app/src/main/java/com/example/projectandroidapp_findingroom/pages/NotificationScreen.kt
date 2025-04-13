package com.example.projectandroidapp_findingroom.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectandroidapp_findingroom.R
import com.example.projectandroidapp_findingroom.ui.theme.fontFamily
@Preview(showBackground = true)
@Composable
fun NotificationScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ){
        Column {
        HeaderNotifi()
        LazyColumn {
            items (10){
                CardNotifi()
            }
        }
    }
    }
}


@Composable
fun HeaderNotifi() {
    Surface(
        color = colorResource(R.color.main_color),
        modifier = Modifier
            .height(140.dp)
            .fillMaxWidth(),

    ) {
        Text(
            text = "Thông báo",
            textAlign = TextAlign.Center,
            fontFamily = fontFamily,
            color = Color.Black,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(top = 60.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
        )
    }
}
@Composable
fun CardNotifi(){
    Surface (
        color = Color(0xFFFDFDFD),
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .height(100.dp)
            .border(2.dp,Color.Red,RoundedCornerShape(30))
            .clip(shape = RoundedCornerShape(30))
        ) {
        Row (
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
        Icon(
            painter = painterResource(R.drawable.baseline_notifications_24),
            contentDescription = null,
            tint = colorResource(R.color.main_color),
            modifier = Modifier.size(60.dp)

        )
        Text(
            text = "Bạn chưa có thông báo",
            fontSize = 20.sp
        )
    }
    }

}