package com.example.projectandroidapp_findingroom.pages

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.example.firestore.AddRoomState
import com.example.firestore.RoomViewModel
import com.example.projectandroidapp_findingroom.R
import com.example.projectandroidapp_findingroom.Room
import com.example.projectandroidapp_findingroom.pages.CardRoom
import com.example.projectandroidapp_findingroom.pages.HeaderHomeScreen
import com.example.projectandroidapp_findingroom.ui.theme.fontFamily
import com.example.projectandroidapp_findingroom.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditScreen(name: String,roomViewModel: RoomViewModel, navController: NavController,authViewModel: AuthViewModel) {
    val user = FirebaseAuth.getInstance().currentUser
    val displayName = user?.email?.substringBefore("@") ?: "Không rõ tên"
    val roomList by roomViewModel.roomList.collectAsState()
    val authorList = roomList.filter { it.author == displayName }
    if (authorList.isEmpty()) {
        Column {
            HeaderRecycling("Chỉnh sửa phòng", navController)
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .width(400.dp).height(400.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.empty),
                        contentDescription = null,
                    ) }
                Text(
                    "Bạn chưa đăng phòng lên!",
                    fontSize = 30.sp
                    )
            }

        }

    }
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 0.dp)
        ) {
            HeaderRecycling("Chỉnh sửa phòng", navController)
            LazyColumn(modifier = Modifier.fillMaxHeight().padding(bottom = 0.dp)) {
                items(authorList.size) { index ->
                    CardRoomForEdit(room = authorList[index], roomViewModel, navController)
                }
            }
        }
    }
}

@Composable
fun CardRoomForEdit(room: Room, roomViewModel: RoomViewModel, navController: NavController) {
    val context = LocalContext.current
    val images = room.urlImage
    val address = room.address
    val price = room.price
    val state = room.state
    Surface(
        shape = RoundedCornerShape(17.dp),
        shadowElevation = 10.dp,
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 30.dp, bottom = 30.dp)
            .clickable {
                navController.navigate("edit/${room.id}")
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(images[0])
                    .crossfade(true)
                    .build(),
                contentDescription = "Uploaded Image",
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop,
                onError = {
                    Log.e("ImageUpload", "Lỗi tải ảnh Coil: ${it.result.throwable.message}")
                    Toast.makeText(context, "Lỗi hiển thị ảnh", Toast.LENGTH_SHORT).show()
                }
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
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Giá: ${price}đ",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(10.dp).fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(Modifier.padding(5.dp))
            }
        }
    }
}
