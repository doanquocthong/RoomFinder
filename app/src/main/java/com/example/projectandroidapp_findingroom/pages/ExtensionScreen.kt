package com.example.projectandroidapp_findingroom.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firestore.RoomViewModel
import com.example.projectandroidapp_findingroom.R
import com.example.projectandroidapp_findingroom.Room
import com.example.projectandroidapp_findingroom.ui.theme.fontFamily
import com.example.projectandroidapp_findingroom.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ExtensionScreen(roomViewModel: RoomViewModel,navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser
    val name = user?.displayName ?: "Tên không xác định"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.main_color))
            .padding(top = 80.dp, bottom = 200.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ){
        HeaderUI()
        EditRoomUI(name, navController)
        AddRoomUI(navController)
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
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
fun EditRoomUI(name: String,navController: NavController) {
    Surface(
        shape = RoundedCornerShape(30.dp),
        color = Color.White,
        modifier = Modifier
            .height(150.dp)
            .width(150.dp)
            .padding()
            .clickable {
                navController.navigate("author/${name}")
            },
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sửa phòng",
                modifier = Modifier
                    .padding(),
                fontSize = 20.sp,
                fontFamily = fontFamily
            )
            Image(
                painter = painterResource(R.drawable.baseline_edit_24),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
            )
        }

    }
}

@Composable
fun AddRoomUI(navController: NavController) {
    Surface(
        shape = RoundedCornerShape(30.dp),
        color = Color.White,
        modifier = Modifier
            .height(150.dp)
            .width(150.dp)
            .clickable {
                navController.navigate("add")
            },
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
                    .padding(top = 20.dp),
                fontSize = 20.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold
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


