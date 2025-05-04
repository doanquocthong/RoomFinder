package com.example.projectandroidapp_findingroom

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
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddRoomScreen(roomViewModel: RoomViewModel, navController: NavController) {
    val context = LocalContext.current
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var isUploading by remember { mutableStateOf(false) }

    // Trạng thái các trường
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var price by remember { mutableStateOf(TextFieldValue("")) }
    var address by remember { mutableStateOf(TextFieldValue("")) }
    var numberOfPeople by remember { mutableStateOf(TextFieldValue("")) }
    var serviceFee by remember { mutableStateOf(TextFieldValue("")) }
    var cleaningFee by remember { mutableStateOf(TextFieldValue("")) }
    var electricFee by remember { mutableStateOf(TextFieldValue("")) }
    var waterFee by remember { mutableStateOf(TextFieldValue("")) }
    var protectFee by remember { mutableStateOf(TextFieldValue("")) }
    var hasBasicInterior by remember { mutableStateOf(false) }
    var hasSofa by remember { mutableStateOf(false) }
    var hasRefrigerator by remember { mutableStateOf(false) }
    var hasHotWater by remember { mutableStateOf(false) }
    var hasWardrobe by remember { mutableStateOf(false) }
    var hasBed by remember { mutableStateOf(false) }

    // Lắng nghe trạng thái thêm phòng
    LaunchedEffect(Unit) {
        roomViewModel.addRoomState.collectLatest { state ->
            when (state) {
                is AddRoomState.Success -> {
                    Toast.makeText(context, "Phòng đã được thêm thành công", Toast.LENGTH_SHORT).show()

                    roomViewModel.resetAddRoomState()
                }
                is AddRoomState.Error -> {
                    Toast.makeText(context, "Lỗi: ${state.message}", Toast.LENGTH_SHORT).show()
                    roomViewModel.resetAddRoomState()
                }
                is AddRoomState.Idle -> {}
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        imageUris = imageUris + uris
        Log.d("AddRoomScreen", "Đã chọn ảnh: $uris")
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) galleryLauncher.launch("image/*")
        else Toast.makeText(context, "Quyền truy cập bị từ chối", Toast.LENGTH_SHORT).show()
    }

    val requestPermission = {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else android.Manifest.permission.READ_EXTERNAL_STORAGE

        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            galleryLauncher.launch("image/*")
        } else {
            permissionLauncher.launch(permission)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val scrollState = rememberScrollState()
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            HeaderRecycling("THÊM PHÒNG")
            BodyAddScreen(
                imageUris = imageUris,
                description = description,
                onDescChange = { description = it },
                price = price,
                onPriceChange = { price = it },
                address = address,
                onAddressChange = { address = it },
                numberOfPeople = numberOfPeople,
                onPeopleChange = { numberOfPeople = it },
                serviceFee = serviceFee,
                onServiceChange = { serviceFee = it },
                cleaningFee = cleaningFee,
                onCleaningChange = { cleaningFee = it },
                electricFee = electricFee,
                onElectricChange = { electricFee = it },
                waterFee = waterFee,
                onWaterChange = { waterFee = it },
                protectFee = protectFee,
                onProtectChange = { protectFee = it },
                hasBasicInterior = hasBasicInterior,
                onBasicInteriorChange = { hasBasicInterior = it },
                hasSofa = hasSofa,
                onSofaChange = { hasSofa = it },
                hasRefrigerator = hasRefrigerator,
                onRefrigeratorChange = { hasRefrigerator = it },
                hasHotWater = hasHotWater,
                onHotWaterChange = { hasHotWater = it },
                hasWardrobe = hasWardrobe,
                onWardrobeChange = { hasWardrobe = it },
                hasBed = hasBed,
                onBedChange = { hasBed = it },
                onAddImageClick = requestPermission
            )
            ButtonAdd(
                navController,
                isLoading = isUploading,
                onClick = {
                    if (!isNetworkAvailable(context)) {
                        Toast.makeText(context, "Không có kết nối mạng", Toast.LENGTH_SHORT).show()
                        return@ButtonAdd
                    }
                    if (imageUris.isEmpty()) {
                        Toast.makeText(context, "Vui lòng chọn ít nhất một ảnh", Toast.LENGTH_SHORT).show()
                        return@ButtonAdd
                    }
                    if (description.text.isBlank() || price.text.isBlank() || address.text.isBlank() || numberOfPeople.text.isBlank()) {
                        Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                        return@ButtonAdd
                    }
                    isUploading = true
                    uploadImagesToCloudinary(
                        context = context,
                        imageUris = imageUris,
                        onComplete = { imageUrls ->
                            isUploading = false
                            if (imageUrls.isEmpty()) {
                                Toast.makeText(context, "Không có ảnh nào được tải lên", Toast.LENGTH_SHORT).show()
                                return@uploadImagesToCloudinary
                            }
                            navController.navigate("main")
                            roomViewModel.addRoom(
                                Room(
                                    urlImage = imageUrls,
                                    description = description.text,
                                    price = price.text.toIntOrNull() ?: 0,
                                    address = address.text,
                                    numberOfPeople = numberOfPeople.text.toIntOrNull() ?: 0,
                                    serviceFee = serviceFee.text.toIntOrNull() ?: 0,
                                    cleaningFee = cleaningFee.text.toIntOrNull() ?: 0,
                                    electricFee = electricFee.text.toIntOrNull() ?: 0,
                                    waterFee = waterFee.text.toIntOrNull() ?: 0,
                                    protectFee = protectFee.text.toIntOrNull() ?: 0,
                                    hasBasicInterior = hasBasicInterior,
                                    hasSofa = hasSofa,
                                    hasRefrigerator = hasRefrigerator,
                                    hasHotWater = hasHotWater,
                                    hasWaredrobe = hasWardrobe,
                                    hasBed = hasBed,

                                )
                            )
                        },
                        onError = { errorMessage ->
                            isUploading = false
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            )
        }
    }
}
@Composable
fun BodyAddScreen(
    imageUris: List<Uri>,
    description: TextFieldValue,
    onDescChange: (TextFieldValue) -> Unit,
    price: TextFieldValue,
    onPriceChange: (TextFieldValue) -> Unit,
    address: TextFieldValue,
    onAddressChange: (TextFieldValue) -> Unit,
    numberOfPeople: TextFieldValue,
    onPeopleChange: (TextFieldValue) -> Unit,
    serviceFee: TextFieldValue,
    onServiceChange: (TextFieldValue) -> Unit,
    cleaningFee: TextFieldValue,
    onCleaningChange: (TextFieldValue) -> Unit,
    electricFee: TextFieldValue,
    onElectricChange: (TextFieldValue) -> Unit,
    waterFee: TextFieldValue,
    onWaterChange: (TextFieldValue) -> Unit,
    protectFee: TextFieldValue,
    onProtectChange: (TextFieldValue) -> Unit,
    hasBasicInterior: Boolean,
    onBasicInteriorChange: (Boolean) -> Unit,
    hasSofa: Boolean,
    onSofaChange: (Boolean) -> Unit,
    hasRefrigerator: Boolean,
    onRefrigeratorChange: (Boolean) -> Unit,
    hasHotWater: Boolean,
    onHotWaterChange: (Boolean) -> Unit,
    hasWardrobe: Boolean,
    onWardrobeChange: (Boolean) -> Unit,
    hasBed: Boolean,
    onBedChange: (Boolean) -> Unit,
    onAddImageClick: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            text = "Chọn ảnh",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.height(300.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .size(100.dp)
                        .clickable { onAddImageClick() }
                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_add_24),
                        contentDescription = "Thêm ảnh",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            items(imageUris) { uri ->
                AsyncImage(
                    model = ImageRequest.Builder(context).data(uri).crossfade(true).build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        SectionText("Mô tả")
        TextFieldOutlined(description, onDescChange)
        SectionText("Giá")
        TextFieldOutlined(price, onPriceChange)
        SectionText("Địa chỉ")
        TextFieldOutlined(address, onAddressChange)
        SectionText("Số người ở")
        TextFieldOutlined(numberOfPeople, onPeopleChange)
        SectionText("Phí dịch vụ")
        TextFieldOutlined(serviceFee, onServiceChange)
        SectionText("Phí vệ sinh")
        TextFieldOutlined(cleaningFee, onCleaningChange)
        SectionText("Giá điện")
        TextFieldOutlined(electricFee, onElectricChange)
        SectionText("Giá nước")
        TextFieldOutlined(waterFee, onWaterChange)
        SectionText("Phí bảo vệ")
        TextFieldOutlined(protectFee, onProtectChange)

        SectionText("Nội thất cơ bản")
        CheckboxRow("Có nội thất", hasBasicInterior, onBasicInteriorChange)
        if (hasBasicInterior) {
            CheckboxRow("Sofa", hasSofa, onSofaChange)
            CheckboxRow("Tủ lạnh", hasRefrigerator, onRefrigeratorChange)
            CheckboxRow("Nước nóng lạnh", hasHotWater, onHotWaterChange)
            CheckboxRow("Tủ đồ", hasWardrobe, onWardrobeChange)
            CheckboxRow("Giường", hasBed, onBedChange)
        }
    }
}

@Composable
fun TextFieldOutlined(
    text: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}

@Composable
fun SectionText(label: String) {
    Text(
        text = label,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.padding(top = 10.dp)
    )
}

@Composable
fun CheckboxRow(
    label: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
        Text(text = label)
    }
}

@Composable
fun ButtonAdd(
    navController: NavController,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.black)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Text(
                text = "Thêm phòng",
                modifier = Modifier.padding(5.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun HeaderRecycling(text: String) {
    Surface(
        color = colorResource(R.color.main_color),
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 60.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
        )
    }
}

fun uploadImagesToCloudinary(
    context: Context,
    imageUris: List<Uri>,
    onComplete: (List<String>) -> Unit,
    onError: (String) -> Unit
) {
    val uploadedUrls = mutableListOf<String>()
    var uploadCount = 0

    if (imageUris.isEmpty()) {
        onComplete(emptyList())
        return
    }

    imageUris.forEach { uri ->
        MediaManager.get().upload(uri)
            .callback(object : com.cloudinary.android.callback.UploadCallback {
                override fun onStart(requestId: String?) {
                    Log.d("CloudinaryUpload", "Bắt đầu tải: $uri")
                }

                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                    Log.d("CloudinaryUpload", "Tiến độ: $bytes/$totalBytes cho $uri")
                }

                override fun onSuccess(requestId: String?, resultData: Map<*, *>) {
                    val url = resultData["secure_url"] as? String
                    if (url != null) {
                        uploadedUrls.add(url)
                        Log.d("CloudinaryUpload", "Tải thành công: $url")
                    } else {
                        Log.e("CloudinaryUpload", "Không có secure_url trong kết quả: $resultData")
                    }
                    uploadCount++
                    if (uploadCount == imageUris.size) {
                        onComplete(uploadedUrls)
                    }
                }

                override fun onError(requestId: String?, error: ErrorInfo?) {
                    Log.e("CloudinaryUpload", "Lỗi tải $uri: ${error?.description}")
                    uploadCount++
                    if (uploadCount == imageUris.size) {
                        onComplete(uploadedUrls)
                    }
                    onError("Lỗi tải ảnh: ${error?.description ?: "Không xác định"}")
                }

                override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                    Log.w("CloudinaryUpload", "Tải bị hoãn $uri: ${error?.description}")
                    onError("Tải bị hoãn: ${error?.description ?: "Không xác định"}")
                }
            })
            .dispatch(context)
    }
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET)
}