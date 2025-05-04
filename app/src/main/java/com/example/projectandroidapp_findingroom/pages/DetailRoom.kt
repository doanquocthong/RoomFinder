package com.example.projectandroidapp_findingroom.pages
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import com.example.projectandroidapp_findingroom.R
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview (showBackground = true)
@Composable
fun DetailRoom(){

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    )
    {
        item {
            Top()
        }
        item {
            Infor()
        }
        item {
            Text(
                text = "Phí dịch vụ",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Column {
                Row(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    ServiceItem(R.drawable.wifi, "Internet\n150k/tháng")
                    ServiceItem(R.drawable.trash, "Vệ sinh\n200k/tháng")
                    ServiceItem(R.drawable.__icon__bolt_, "Điện\n3500/W")
                }
                Row(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    ServiceItem(R.drawable.__icon__water_, "Nước\n3000/khối")
                    ServiceItem(R.drawable.__icon__shield_, "Bảo vệ\n200k/tháng")
                }
                Spacer(modifier = Modifier.height(10.dp))
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
                    ServiceItem(R.drawable.solar_sofa_linear, "Sofa")
                    ServiceItem(R.drawable.ion_bed_sharp, "Gường")
                    ServiceItem(R.drawable.streamline_hotel_shower_head_solid, "Nước nóng/lạnh")
                }
                Row(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    ServiceItem(R.drawable.air, "Máy lạnh")
                    ServiceItem(R.drawable.icon_park_solid_file_cabinet, "Tủ đồ")
                    ServiceItem(R.drawable.mdi_fridge, "Tủ lạnh")
                }


            }

        }

    }}



@Composable
fun Top() {
    Box(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .background(colorResource(R.color.main_color)),
        contentAlignment = Alignment.CenterStart
    ) {


        Surface(
            color = colorResource(R.color.main_color),
            modifier = Modifier.padding(30.dp)
                .clickable { }
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )


        }

    }
}
@Composable
fun Infor () {

    Box(
        modifier = Modifier.fillMaxWidth().height(600.dp)
    ) {
        Column {
            Image(
                painter = painterResource(R.drawable.anh1),
                contentDescription = null,
                modifier = Modifier.height(286.dp).width(600.dp)
            )
            Text(
                text = "Biệt phủ theo phong cách Hà Nội - Full nội thất",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = "Giá: 2.000.000đ",
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
                    painter = painterResource(R.drawable.local),
                    contentDescription = null,
                    tint = colorResource(R.color.main_color)
                )
                Text(
                    text = "597/14 Quang Trung, phường 16, Gò Vấp, Thành phố Hồ Chí Minh",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.__icon__clock_),
                    contentDescription = null,
                    tint = colorResource(R.color.teal_200)
                )
                Text(
                    text = "Tự do",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 5.dp)
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
