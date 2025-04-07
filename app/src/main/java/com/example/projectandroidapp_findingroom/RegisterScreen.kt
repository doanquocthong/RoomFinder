package com.example.projectandroidapp_findingroom



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.projectandroidapp_findingroom.ui.theme.fontFamily


@Composable
fun RegisterScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.main_color)),

        ) {
        Column(
            modifier = Modifier
                .padding(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(modifier = Modifier.height(50.dp))
            Icon(
                painter = painterResource(R.drawable.home_img),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Text(
                text = "Tìm Nhà Trọ",
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp),
                fontSize = 25.sp,
                fontFamily = fontFamily
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = "Tên Đăng Nhập",
                onValueChange = {},
                textStyle = TextStyle(
                    fontFamily = fontFamily,
                    color = Color.Gray,
                    fontSize = 14.sp
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,  // Ẩn gạch dưới khi focus
                    unfocusedIndicatorColor = Color.Transparent // Ẩn gạch dưới khi không focus
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(23.dp))
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = "Mật Khẩu",
                onValueChange = {},
                textStyle = TextStyle(
                    fontFamily = fontFamily,
                    color = Color.Gray,
                    fontSize = 14.sp
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(23.dp))
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = "Nhập lại mật Khẩu",
                onValueChange = {},
                textStyle = TextStyle(
                    fontFamily = fontFamily,
                    color = Color.Gray,
                    fontSize = 14.sp
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(23.dp))
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = false,
                    onCheckedChange = {},
                    colors = CheckboxDefaults.colors(
                        checkedColor = colorResource(R.color.white),
                        uncheckedColor = colorResource(R.color.checkbox_color),
                        checkmarkColor = Color.White
                    )


                    )
                Text(
                    text = "Hiện Mật Khẩu",
                    fontSize = 15.sp,
                    fontFamily = fontFamily
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {},
                    modifier = Modifier
                        .width(150.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = colorResource(R.color.main_color)
                    ))
                {
                    Text(
                        text = "Xác Nhận",
                        fontFamily = fontFamily,
                        fontSize = 15.sp,
                        color = colorResource(R.color.main_color),
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Bạn có tài khoản ?Đăng nhập",
                fontSize = 15.sp,
                fontFamily = fontFamily,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.End,
            )

        }
    }
}