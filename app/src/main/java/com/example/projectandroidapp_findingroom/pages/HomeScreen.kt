package com.example.projectandroidapp_findingroom.pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.firestore.RoomViewModel
import com.example.projectandroidapp_findingroom.R
import com.example.projectandroidapp_findingroom.Room
import com.example.projectandroidapp_findingroom.ui.theme.fontFamily

@Composable
fun HomeScreen(roomViewModel: RoomViewModel, navController: NavController) {
    val roomList by roomViewModel.roomList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp)
    ){
        HeaderHomeScreen()
        LazyColumn (modifier = Modifier.fillMaxHeight()){
            items(roomList.size) { index ->
                CardRoom(room = roomList[index], roomViewModel, navController)
            }
        }

    }
}

@Composable
fun HeaderHomeScreen() {
    var searchInput by remember { mutableStateOf(TextFieldValue(""))}
    Surface(
        color = colorResource(R.color.main_color),
        modifier = Modifier
            .height(140.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = searchInput,
            onValueChange = {
                searchInput = it
            },
            placeholder = { Text("Tìm kiếm") },
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

@Composable
fun CardRoom(room: Room, roomViewModel: RoomViewModel, navController: NavController) {
    val context = LocalContext.current
    val images = room.urlImage
    val address = room.address
    val price = room.price
    val state = room.state
    Surface(
        shape = RoundedCornerShape(17.dp),
        shadowElevation = 10.dp,
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 30.dp)
            .clickable {
                navController.navigate("detail/${room.id}")
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
                            "Phòng còn trống"
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