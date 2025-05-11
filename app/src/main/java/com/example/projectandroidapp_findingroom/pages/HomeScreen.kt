package com.example.projectandroidapp_findingroom.pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.font.FontWeight
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
    var searchInput by remember { mutableStateOf(TextFieldValue("")) }

    var selectedDistrict by remember { mutableStateOf("") }
    val districts = listOf(
        // Thành phố
        "Thành phố Thủ Đức",

        // Các quận nội thành
        "Quận 1",
        "Quận 3",
        "Quận 4",
        "Quận 5",
        "Quận 6",
        "Quận 7",
        "Quận 8",
        "Quận 10",
        "Quận 11",
        "Quận 12",
        "Quận Bình Tân",
        "Quận Bình Thạnh",
        "Quận Gò Vấp",
        "Quận Phú Nhuận",
        "Quận Tân Bình",
        "Quận Tân Phú",

        // Các huyện ngoại thành
        "Huyện Bình Chánh",
        "Huyện Cần Giờ",
        "Huyện Củ Chi",
        "Huyện Hóc Môn",
        "Huyện Nhà Bè"
    )

    fun removeVietnameseAccents(text: String): String {
        return text
            .replace("á", "a").replace("à", "a").replace("ả", "a").replace("ã", "a").replace("ạ", "a")
            .replace("ă", "a").replace("ắ", "a").replace("ằ", "a").replace("ẳ", "a").replace("ẵ", "a").replace("ặ", "a")
            .replace("â", "a").replace("ấ", "a").replace("ầ", "a").replace("ẩ", "a").replace("ẫ", "a").replace("ậ", "a")
            .replace("é", "e").replace("è", "e").replace("ẻ", "e").replace("ẽ", "e").replace("ẹ", "e")
            .replace("ê", "e").replace("ế", "e").replace("ề", "e").replace("ể", "e").replace("ễ", "e").replace("ệ", "e")
            .replace("í", "i").replace("ì", "i").replace("ỉ", "i").replace("ĩ", "i").replace("ị", "i")
            .replace("ó", "o").replace("ò", "o").replace("ỏ", "o").replace("õ", "o").replace("ọ", "o")
            .replace("ô", "o").replace("ố", "o").replace("ồ", "o").replace("ổ", "o").replace("ỗ", "o").replace("ộ", "o")
            .replace("ơ", "o").replace("ớ", "o").replace("ờ", "o").replace("ở", "o").replace("ỡ", "o").replace("ợ", "o")
            .replace("ú", "u").replace("ù", "u").replace("ủ", "u").replace("ũ", "u").replace("ụ", "u")
            .replace("ư", "u").replace("ứ", "u").replace("ừ", "u").replace("ử", "u").replace("ữ", "u").replace("ự", "u")
            .replace("ý", "y").replace("ỳ", "y").replace("ỷ", "y").replace("ỹ", "y").replace("ỵ", "y")
            .replace("đ", "d")
    }
//    fun removeVietnameseAccents(text: String): String {
//        val withoutAccents = text.replace(Regex("[áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđ]"), "")
//        return withoutAccents
//    }
//    val filteredList = roomList.filter {
//        val matchesSearch = it.address.contains(searchInput.text.trim(), ignoreCase = true)
//        val matchesDistrict = selectedDistrict.isEmpty() || it.address.contains(selectedDistrict, ignoreCase = true)
//        matchesSearch && matchesDistrict
//    }
    val filteredList = roomList.filter {
        val matchesSearch = removeVietnameseAccents(it.address).contains(removeVietnameseAccents(searchInput.text.trim()), ignoreCase = true)
        val matchesDistrict = selectedDistrict.isEmpty() || removeVietnameseAccents(it.address).contains(removeVietnameseAccents(selectedDistrict), ignoreCase = true)
        matchesSearch && matchesDistrict
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp)
    ) {
        HeaderHomeScreen(searchInput) { newValue ->
            searchInput = newValue
        }
        LazyRow (modifier = Modifier.fillMaxWidth()) {
            items(districts.size) { index ->
                District(
                    district = districts[index],
                    isSelected = districts[index] == selectedDistrict,
                    onClick = {
                        selectedDistrict = if (selectedDistrict == districts[index]) "" else districts[index]
                    }
                )
            }
        }
        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            items(filteredList.size) { index ->
                CardRoom(room = filteredList[index], roomViewModel, navController)
            }
        }
    }
}

@Composable
fun District(district: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.padding(10.dp).clickable { onClick() },
        shadowElevation = 2.dp,
        color = if (isSelected) Color.Gray else Color.White
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                "${district}",
                modifier = Modifier.padding(10.dp),
                fontSize = 15.sp,
                color = if (isSelected) Color.White else Color.Black
            )
        }

    }
}

@Composable
fun HeaderHomeScreen(searchInput: TextFieldValue, onSearchInputChange: (TextFieldValue) -> Unit) {
    Surface(
        color = colorResource(R.color.main_color),
        modifier = Modifier
            .height(140.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = searchInput,
            onValueChange = onSearchInputChange,
            placeholder = { Text("Tìm kiếm địa chỉ...") },
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
            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
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