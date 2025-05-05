package com.example.projectandroidapp_findingroom.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import com.example.projectandroidapp_findingroom.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectandroidapp_findingroom.ui.theme.fontFamily
import com.example.projectandroidapp_findingroom.viewmodel.AuthState
import com.example.projectandroidapp_findingroom.viewmodel.AuthViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AccountScreen(navController: NavController, authViewModel: AuthViewModel){
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ){

        Profile(navController, authViewModel)
    }

}

@Composable
fun Profile(navController: NavController, authViewModel: AuthViewModel){
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }
    val user = FirebaseAuth.getInstance().currentUser
    val displayName = user?.displayName ?: "Không rõ tên"
    val avatarUri = user?.photoUrl?.toString()
    val email = user?.email ?: "Không có email"
    val phoneNumber = user?.phoneNumber ?: "Không có số điện thoại"
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = colorResource(R.color.main_color))

    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize().padding(top = 150.dp)
        ){
            if (avatarUri != null) {
                AsyncImage(
                    model = avatarUri,
                    contentDescription = "Avatar người dùng",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Avatar mặc định nếu người dùng không có ảnh
                Icon(
                    painter = painterResource(R.drawable.baseline_person_24),
                    contentDescription = "Avatar mặc định",
                    modifier = Modifier
                        .size(120.dp)
                )
            }
            Text(
                text = "Tên : $displayName",
                fontSize = 18.sp,
                fontFamily = fontFamily,
                modifier = Modifier.padding(10.dp)
            )
            Text(text = "Email: $email",
                fontSize = 18.sp,
                modifier = Modifier.padding(10.dp))
            Text(
                text = "Số điện thoại : $phoneNumber",
                fontSize = 18.sp,
                modifier = Modifier.padding(10.dp)
            )
            Spacer(modifier = Modifier.height(150.dp))
            Surface (

                modifier = Modifier
                    .height(50.dp)
                    .width(300.dp)
                    .clip(shape = RoundedCornerShape(40))
                    .clickable {
                        FirebaseAuth.getInstance().signOut()
                        authViewModel.signout()

                    },
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