package com.example.projectandroidapp_findingroom.pages

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.firestore.RoomViewModel
import com.example.projectandroidapp_findingroom.R
import com.example.projectandroidapp_findingroom.Room
import com.example.projectandroidapp_findingroom.ui.theme.fontFamily
import kotlinx.coroutines.launch

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
    HeaderRecycling("Chi tiết phòng",navController)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 120.dp)
    ) {
        item {
            // Banner trượt cho hình ảnh phòng
            if (room.urlImage.isNotEmpty()) {
                ImageSlider(images = room.urlImage)
            } else {
                Text(
                    text = "Không có ảnh",
                    fontFamily = fontFamily,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .wrapContentSize(Alignment.Center)
                )
            }
        }
        item {
        Column (
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
        ){
            Text(
                text = "Mô tả: " + room.description,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = "Giá: " + room.price,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = colorResource(R.color.teal_700),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_location_on_24),
                    contentDescription = null,
                    tint = colorResource(R.color.main_color),
                    modifier = Modifier
                        .size(40.dp)
                )
                Text(
                    text = room.address,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_people_outline_24),
                    contentDescription = null,
                    tint = colorResource(R.color.teal_200),
                    modifier = Modifier
                        .size(40.dp)
                )
                Text(
                    text = "Phù hợp " + room.numberOfPeople.toString() + " người ở",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(color = colorResource(R.color.main_color))
            )
        }
            Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Phí dịch vụ",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        )
        Column (
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        ){
            Row(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ServiceItem(R.drawable.wifi, "Internet ${room.internetFee}/tháng")
                ServiceItem(R.drawable.trash, "Vệ sinh ${room.cleaningFee}/tháng")
                ServiceItem(R.drawable.__icon__bolt_, "Điện ${room.electricFee}k/khối")
            }
            Row(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ServiceItem(R.drawable.__icon__water_, "Nước ${room.waterFee}/người")
                ServiceItem(R.drawable.__icon__shield_, "Bảo vệ ${room.protectFee}/tháng")
            }
            Spacer(modifier = Modifier.height(10.dp))

            if (room.hasBasicInterior) {
                Text(
                    text = "Nội thất",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if(room.hasSofa) {
                        ServiceItem(R.drawable.solar_sofa_linear, "Sofa")
                    }
                    if(room.hasBed) {
                        ServiceItem(R.drawable.ion_bed_sharp, "Gường")
                    }
                    if(room.hasHotWater) {
                        ServiceItem(R.drawable.streamline_hotel_shower_head_solid, "Nước nóng/lạnh")
                    }
                }
                Row(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if(room.hasRefrigerator) {
                        ServiceItem(R.drawable.air, "Máy lạnh")
                        ServiceItem(R.drawable.mdi_fridge, "Tủ lạnh")
                    }
                    if(room.hasWaredrobe) {
                        ServiceItem(R.drawable.icon_park_solid_file_cabinet, "Tủ đồ")
                    }
                }
            }
        }
            ButtonHotline("0945946668")
    }
    }
}

@Composable
fun ImageSlider(images: List<String>) {
    val pagerState = rememberPagerState(pageCount = { images.size })
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        // HorizontalPager để hiển thị ảnh
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(images[page])
                    .crossfade(true)
                    .build(),
                contentDescription = "Room Image",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )
        }

        // Nút điều hướng trái
        if (images.size > 1) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
                    .size(40.dp)
                    .clip(CircleShape),
                enabled = pagerState.currentPage > 0,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black.copy(alpha = 0.5f),
                    disabledContainerColor = Color.Gray.copy(alpha = 0.3f)
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = "<", color = Color.White, fontSize = 20.sp)
            }

            // Nút điều hướng phải
            Button(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
                    .size(40.dp)
                    .clip(CircleShape),
                enabled = pagerState.currentPage < images.size - 1,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black.copy(alpha = 0.5f),
                    disabledContainerColor = Color.Gray.copy(alpha = 0.3f)
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = ">", color = Color.White, fontSize = 20.sp)
            }

            // Chỉ số trang (dots)
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(images.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == pagerState.currentPage) Color.White
                                else Color.Gray.copy(alpha = 0.5f)
                            )
                    )
                }
            }
        }
    }
}
@Composable
fun ServiceItem(iconResId: Int, label: String) {
    Column(
        modifier = Modifier
            .height(150.dp)
            .width(100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            color = colorResource(R.color.main_color),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.size(80.dp),
            tonalElevation = 4.dp,
            shadowElevation = 4.dp
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = null,
                    tint = colorResource(R.color.white),
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = label,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun ButtonHotline(telephoneNumber: String) {
    val context = LocalContext.current

    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$telephoneNumber")
            }
            context.startActivity(intent)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.black)
        )
    ) {
        Text(
            text = "Liên hệ: $telephoneNumber",
            modifier = Modifier.padding(10.dp),
            fontSize = 20.sp,
        )
    }
}