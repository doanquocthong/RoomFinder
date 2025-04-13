package com.example.projectandroidapp_findingroom.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.projectandroidapp_findingroom.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectandroidapp_findingroom.ui.theme.fontFamily

@Composable
fun AccountScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ){

        Profile()
    }

}
@Preview(showBackground = true)
@Composable
fun Profile(){
    val name = "huy"
//    val user = FirebaseAuth.getInstance().currentUser
//    val avatarUri = user?.photoUrl?.toString() ?: ""
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = colorResource(R.color.main_color))

    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize().padding(top = 150.dp)
        ){
            Surface(
                color = Color.LightGray,
                modifier = Modifier.height(125.dp).width(125.dp).clip(shape = RoundedCornerShape(90))
            ) {
                Icon(
//                  painter = rememberAsyncImagePainter(avatarUri),
                    painter = painterResource(R.drawable.baseline_person_24),
                    contentDescription = null,

                    )
            }
            Text(
                text = "$name",
                fontSize = 30.sp,
                fontFamily = fontFamily,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = "0955448899",
                fontSize = 30.sp,
                modifier = Modifier.padding(10.dp)
            )
            Spacer(modifier = Modifier.height(150.dp))
            Surface (

                modifier = Modifier.height(50.dp).width(300.dp).clip(shape = RoundedCornerShape(40)),
                color = Color.Black
            ){

                Text(
                    text = "Đăng xuất" ,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontFamily = fontFamily,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

        }
    }
}