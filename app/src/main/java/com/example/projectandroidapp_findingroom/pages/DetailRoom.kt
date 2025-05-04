package com.example.projectandroidapp_findingroom.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.firestore.RoomViewModel
import com.example.projectandroidapp_findingroom.Room
import com.example.projectandroidapp_findingroom.ui.theme.fontFamily

@Composable
fun DetailRoom(roomId: String, roomViewModel: RoomViewModel, navController: NavController) {
    val roomList by roomViewModel.roomList.collectAsState()
    val room = roomList.find { it.id == roomId }

    if (room == null) {
        Text(
            text = "Không tìm thấy phòng",
            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center),
            fontSize = 20.sp,
            fontFamily = fontFamily
        )
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            // Hình ảnh phòng
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(room.urlImage.firstOrNull())
                    .crossfade(true)
                    .build(),
                contentDescription = "Room Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(bottom = 16.dp),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )

            // Thông tin cơ bản
            Text(
                text = "Địa chỉ: ${room.address}",
                fontFamily = fontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Giá: ${room.price} VND",
                fontFamily = fontFamily,
                fontSize = 16.sp,
                color = Color.Blue
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (room.state) "Phòng còn trống" else "Phòng đã có người cọc",
                fontFamily = fontFamily,
                fontSize = 16.sp,
                color = if (room.state) Color.Green else Color.Red
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Chi tiết chi phí
            Text(
                text = "Chi tiết chi phí",
                fontFamily = fontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Phí dịch vụ: ${room.serviceFee} VND", fontFamily = fontFamily)
            Text(text = "Phí dọn dẹp: ${room.cleaningFee} VND", fontFamily = fontFamily)
            Text(text = "Phí điện: ${room.electricFee} VND", fontFamily = fontFamily)
            Text(text = "Phí nước: ${room.waterFee} VND", fontFamily = fontFamily)
            Text(text = "Phí bảo vệ: ${room.protectFee} VND", fontFamily = fontFamily)

            Spacer(modifier = Modifier.height(16.dp))

            // Nội thất
            Text(
                text = "Nội thất",
                fontFamily = fontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Nội thất cơ bản: ${if (room.hasBasicInterior) "Có" else "Không"}",
                fontFamily = fontFamily
            )
            Text(text = "Sofa: ${if (room.hasSofa) "Có" else "Không"}", fontFamily = fontFamily)
            Text(
                text = "Tủ lạnh: ${if (room.hasRefrigerator) "Có" else "Không"}",
                fontFamily = fontFamily
            )
            Text(
                text = "Nước nóng: ${if (room.hasHotWater) "Có" else "Không"}",
                fontFamily = fontFamily
            )
            Text(
                text = "Tủ quần áo: ${if (room.hasWaredrobe) "Có" else "Không"}",
                fontFamily = fontFamily
            )
            Text(text = "Giường: ${if (room.hasBed) "Có" else "Không"}", fontFamily = fontFamily)
            if (room.elseInterior.isNotEmpty()) {
                Text(text = "Khác: ${room.elseInterior}", fontFamily = fontFamily)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Thông tin khác
            Text(
                text = "Thông tin khác",
                fontFamily = fontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Người đăng: ${room.author}", fontFamily = fontFamily)
            Text(text = "Mô tả: ${room.description}", fontFamily = fontFamily)
            Text(text = "Số người: ${room.numberOfPeople}", fontFamily = fontFamily)

            Spacer(modifier = Modifier.height(16.dp))

            // Nút quay lại
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Quay lại", fontFamily = fontFamily)
            }
        }
    }
}