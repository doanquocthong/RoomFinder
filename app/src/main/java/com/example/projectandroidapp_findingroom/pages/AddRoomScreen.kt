package com.example.projectandroidapp_findingroom

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun AddRoomScreen(room: Room) {
    val context = LocalContext.current
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    // Launcher cho kho ảnh
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        imageUris = imageUris + uris // Thêm ảnh mới vào danh sách
        Log.d("AddRoomScreen", "Ảnh được chọn: $uris")
    }

    // Launcher cho quyền
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            Log.d("AddRoomScreen", "Quyền được cấp")
            galleryLauncher.launch("image/*")
        } else {
            Log.w("AddRoomScreen", "Quyền bị từ chối")
            Toast.makeText(context, "Quyền truy cập bộ nhớ bị từ chối", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val scrollState = rememberScrollState()
        Column (
            modifier = Modifier
                .verticalScroll(scrollState)
        ){
            HeaderRecycling("THÊM PHÒNG")
            BodyAddScreen(
                imageUris = imageUris,
                onAddImageClick = {
                    // Kiểm tra phiên bản Android và quyền phù hợp
                    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        android.Manifest.permission.READ_MEDIA_IMAGES
                    } else {
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    }

                    // Kiểm tra trạng thái quyền
                    val permissionStatus = ContextCompat.checkSelfPermission(context, permission)
                    if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                        Log.d("AddRoomScreen", "Quyền đã được cấp: $permission")
                        galleryLauncher.launch("image/*")
                    } else {
                        Log.d("AddRoomScreen", "Yêu cầu quyền: $permission")
                        permissionLauncher.launch(permission)
                    }
                }
            )
            ButtonAdd()
        }
    }
}

@Composable
fun BodyAddScreen(
    imageUris: List<Uri>,
    onAddImageClick: () -> Unit
) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var price by remember { mutableStateOf(TextFieldValue("")) }
    var address by remember { mutableStateOf(TextFieldValue("")) }
    var isCheck by remember { mutableStateOf(false)}
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            "Chọn ảnh",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .height(400.dp)
        ) {
            // Hiển thị nút dấu cộng
            item {
                Surface(
                    shadowElevation = 2.dp,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { onAddImageClick() }
                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_add_24),
                        contentDescription = "Add Image",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            // Hiển thị các ảnh đã chọn
            items(imageUris) { uri ->
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(uri)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(4.dp),
                    onError = {
                        Log.e("AddRoomScreen", "Lỗi tải ảnh Coil: ${it.result.throwable.message}")
                        Toast.makeText(context, "Lỗi hiển thị ảnh", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

        Text("Địa chỉ", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 10.dp))
        TextFieldOutlined(text = title, onTextChange = { title = it })

        Text("Giá", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 10.dp))
        TextFieldOutlined(text = description, onTextChange = { description = it })

        Text("Số phòng trống", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 10.dp))
        TextFieldOutlined(text = price, onTextChange = { price = it })

        Text("Số người ở", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 10.dp))
        TextFieldOutlined(text = address, onTextChange = { address = it })

        Text("Phí dịch vụ", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 10.dp))
        TextFieldOutlined(text = address, onTextChange = { address = it })

        Text("Phí vệ sinh", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 10.dp))
        TextFieldOutlined(text = address, onTextChange = { address = it })

        Text("Giá điện", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 10.dp))
        TextFieldOutlined(text = address, onTextChange = { address = it })

        Text("Giá nước", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 10.dp))
        TextFieldOutlined(text = address, onTextChange = { address = it })

        Text("Phí bảo vệ", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 10.dp))
        TextFieldOutlined(text = address, onTextChange = { address = it })


        Column {
            Text("Nội thất", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 2.dp))
            checkBox(isCheck = isCheck, onCheckedChange = { isCheck = it })
        }
        if(!isCheck) {
            Column (

            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Column {
                        Text("Sofa", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 2.dp))
                        checkBox(isCheck = isCheck, onCheckedChange = { isCheck = it })
                    }
                    Column {
                        Text("Giường", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 2.dp))
                        checkBox(isCheck = isCheck, onCheckedChange = { isCheck = it })
                    }
                }
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Nước nóng/lạnh", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 2.dp))
                        checkBox(isCheck = isCheck, onCheckedChange = { isCheck = it })
                    }
                    Column {
                        Text("Máy lạnh", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 2.dp))
                        checkBox(isCheck = isCheck, onCheckedChange = { isCheck = it })
                    }
                }
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Tủ lạnh", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 2.dp))
                        checkBox(isCheck = isCheck, onCheckedChange = { isCheck = it })
                    }
                    Column {
                        Text("Tủ đồ", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 2.dp))
                        checkBox(isCheck = isCheck, onCheckedChange = { isCheck = it })
                    }
                }
            }
        }

    }
}
@Composable
fun checkBox(
    isCheck: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = isCheck,
            onCheckedChange = onCheckedChange
        )
        Text(text = if (isCheck) "Có nội thất" else "Không có nội thất")
    }
}

@Composable
fun TextFieldOutlined(
    text: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit
) {
    Surface(
        shadowElevation = 2.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            textStyle = TextStyle(
                color = Color.Gray,
                fontSize = 17.sp,
                textAlign = TextAlign.Start
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
            ),
            singleLine = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
    }
}



@Composable
fun ButtonAdd() {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.black)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    ) {
        Text(
            "Thêm phòng",
            modifier = Modifier.padding(5.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HeaderRecycling(text: String) {
    Surface(
        color = colorResource(R.color.main_color),
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth(),

        ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(top = 60.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
        )
    }
}