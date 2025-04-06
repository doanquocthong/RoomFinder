package com.example.projectandroidapp_findingroom



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun RegisterScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize().background(color = colorResource(R.color.main_color)),

        ) {
        Column(modifier = Modifier.fillMaxSize().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Spacer(modifier = Modifier.height(50.dp))
            Icon(
                Icons.Filled.Home,
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Text(
                text = "Tìm Nhà Trọ",
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = "Tên Đăng Nhập",
                onValueChange = {},
                modifier = Modifier.clip(RoundedCornerShape(20.dp))
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = "Mật Khẩu",
                onValueChange = {},
                modifier = Modifier.clip(RoundedCornerShape(20.dp))

            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = "Nhâp lại mật Khẩu",
                onValueChange = {},
                modifier = Modifier.clip(RoundedCornerShape(20.dp))

            )
            Row(modifier = Modifier.fillMaxWidth().offset(x=40.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = false,
                    onCheckedChange = {},

                    )
                Text(
                    text = "Hiện Mật Khẩu",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(onClick = {},
                modifier = Modifier.width(150.dp).offset(x=60.dp),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.black))) {
                Text(
                    text = "Xác Nhận",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = colorResource(R.color.white)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Bạn có tài khoản ? Đăng nhập",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

        }
    }
}