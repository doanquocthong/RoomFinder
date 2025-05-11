import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.firestore.DeleteRoomState
import com.example.firestore.RoomViewModel
import com.example.firestore.UpdateRoomState
import com.example.projectandroidapp_findingroom.CheckboxRow
import com.example.projectandroidapp_findingroom.R
import com.example.projectandroidapp_findingroom.Room
import com.example.projectandroidapp_findingroom.SectionText
import com.example.projectandroidapp_findingroom.TextFieldOutlined
import com.example.projectandroidapp_findingroom.isNetworkAvailable
import com.example.projectandroidapp_findingroom.uploadImagesToCloudinary
import com.example.projectandroidapp_findingroom.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun EditRoom(roomId: String, roomViewModel: RoomViewModel, navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val roomList by roomViewModel.roomList.collectAsState()
    val room = roomList.find { it.id == roomId }
    var isUploading by remember { mutableStateOf(false) }
    val user = FirebaseAuth.getInstance().currentUser
    val displayName = user?.email?.substringBefore("@") ?: "Không rõ tên"
    // Kiểm tra nếu phòng không tồn tại
    if (room == null) {
        Text("Phòng không tồn tại hoặc đã bị xóa.")
        LaunchedEffect(Unit) {
            navController.navigate("author/${displayName}")
        }
        return
    }

    // Trạng thái các trường
    var imageUris by remember { mutableStateOf(room.urlImage.map { Uri.parse(it) }) }
    var description by remember { mutableStateOf(TextFieldValue(room.description)) }
    var price by remember { mutableStateOf(TextFieldValue(room.price)) }
    var address by remember { mutableStateOf(TextFieldValue(room.address)) }
    var numberOfPeople by remember { mutableStateOf(TextFieldValue(room.numberOfPeople)) }
    var internetFee by remember { mutableStateOf(TextFieldValue(room.internetFee)) }
    var cleaningFee by remember { mutableStateOf(TextFieldValue(room.cleaningFee)) }
    var electricFee by remember { mutableStateOf(TextFieldValue(room.electricFee)) }
    var waterFee by remember { mutableStateOf(TextFieldValue(room.waterFee)) }
    var protectFee by remember { mutableStateOf(TextFieldValue(room.protectFee)) }
    var hasBasicInterior by remember { mutableStateOf(room.hasBasicInterior) }
    var hasSofa by remember { mutableStateOf(room.hasSofa) }
    var hasRefrigerator by remember { mutableStateOf(room.hasRefrigerator) }
    var hasHotWater by remember { mutableStateOf(room.hasHotWater) }
    var hasWardrobe by remember { mutableStateOf(room.hasWaredrobe) }
    var hasBed by remember { mutableStateOf(room.hasBed) }
    var telephoneNumber by remember { mutableStateOf(TextFieldValue(room.telephoneNumber)) }


    // Lắng nghe trạng thái cập nhật phòng
    LaunchedEffect(Unit) {
        roomViewModel.updateRoomState.collectLatest { state ->
            when (state) {
                is UpdateRoomState.Success -> {
                    isUploading = false
                    Toast.makeText(context, "Chỉnh sửa phòng thành công", Toast.LENGTH_SHORT).show()
                    roomViewModel.resetUpdateRoomState()
                    navController.navigate("author/${displayName}")
                }
                is UpdateRoomState.Error -> {
                    isUploading = false
                    Toast.makeText(context, "Lỗi khi chỉnh sửa: ${state.message}", Toast.LENGTH_SHORT).show()
                    roomViewModel.resetUpdateRoomState()
                }
                is UpdateRoomState.Idle -> {
                    isUploading = false
                }
            }
        }
    }

    // Lắng nghe trạng thái xóa phòng
    LaunchedEffect(Unit) {
        roomViewModel.deleteRoomState.collectLatest { state ->
            when (state) {
                is DeleteRoomState.Success -> {
                    isUploading = false
                    Toast.makeText(context, "Xóa phòng thành công", Toast.LENGTH_SHORT).show()
                    roomViewModel.resetDeleteRoomState()
                    navController.navigate("author/${displayName}")
                }
                is DeleteRoomState.Error -> {
                    isUploading = false
                    Toast.makeText(context, "Lỗi khi xóa: ${state.message}", Toast.LENGTH_SHORT).show()
                    roomViewModel.resetDeleteRoomState()
                }
                is DeleteRoomState.Loading -> {
                    isUploading = true
                }
                is DeleteRoomState.Idle -> {
                    isUploading = false
                }
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        imageUris = imageUris + uris
        Log.d("EditRoom", "Đã chọn ảnh: $uris")
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
            com.example.projectandroidapp_findingroom.pages.HeaderRecycling(
                "Chỉnh sửa phòng",
                navController,
                "main"
            )
            BodyEdit(
                imageUris = imageUris,
                description = description,
                onDescChange = { description = it },
                price = price,
                onPriceChange = { price = it },
                address = address,
                onAddressChange = { address = it },
                numberOfPeople = numberOfPeople,
                onPeopleChange = { numberOfPeople = it },
                internetFee = internetFee,
                onServiceChange = { internetFee = it },
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
                telephoneNumber = telephoneNumber,
                onTelephoneNumber = { telephoneNumber = it },
                onAddImageClick = requestPermission
            )
            Column {
                ButtonEdit(
                    navController = navController,
                    isLoading = isUploading,
                    onClick = {
                        if (!isNetworkAvailable(context)) {
                            Toast.makeText(context, "Không có kết nối mạng", Toast.LENGTH_SHORT).show()
                            return@ButtonEdit
                        }
                        if (imageUris.isEmpty()) {
                            Toast.makeText(context, "Vui lòng chọn ít nhất một ảnh", Toast.LENGTH_SHORT).show()
                            return@ButtonEdit
                        }
                        if (description.text.isBlank() || price.text.isBlank() || address.text.isBlank()) {
                            Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                            return@ButtonEdit
                        }
                        isUploading = true
                        uploadImagesToCloudinary(
                            context = context,
                            imageUris = imageUris,
                            onComplete = { imageUrls ->
                                if (imageUrls.isEmpty()) {
                                    isUploading = false
                                    Toast.makeText(context, "Không có ảnh nào được tải lên", Toast.LENGTH_SHORT).show()
                                    return@uploadImagesToCloudinary
                                }
                                roomViewModel.updateRoom(
                                    roomId,
                                    Room(
                                        urlImage = imageUrls,
                                        description = description.text,
                                        price = price.text,
                                        address = address.text,
                                        numberOfPeople = numberOfPeople.text,
                                        internetFee = internetFee.text,
                                        cleaningFee = cleaningFee.text,
                                        electricFee = electricFee.text,
                                        waterFee = waterFee.text,
                                        protectFee = protectFee.text,
                                        hasBasicInterior = hasBasicInterior,
                                        hasSofa = hasSofa,
                                        hasRefrigerator = hasRefrigerator,
                                        hasHotWater = hasHotWater,
                                        hasWaredrobe = hasWardrobe,
                                        hasBed = hasBed,
                                        telephoneNumber = telephoneNumber.text,
                                        author = user?.email?.substringBefore("@") ?: "Không rõ tên"
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
                ButtonDelete(
                    navController = navController,
                    isLoading = isUploading,
                    onClick = {
                        if (!isNetworkAvailable(context)) {
                            Toast.makeText(context, "Không có kết nối mạng", Toast.LENGTH_SHORT).show()
                            return@ButtonDelete
                        }
                        roomViewModel.deleteRoom(roomId)
                    }
                )
            }
        }
    }
}

// Hàm debounce để tránh nhấn nút nhiều lần
fun <T> debounce(
    waitMs: Long = 300L,
    scope: CoroutineScope,
    destinationFunction: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(waitMs)
            destinationFunction(param)
        }
    }
}

@Composable
fun ButtonEdit(
    navController: NavController,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val debouncedOnClick = remember { debounce<Unit>(scope = scope) { onClick() } }

    Button(
        onClick = { debouncedOnClick(Unit) },
        enabled = !isLoading,
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.black)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp, bottom = 10.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Text(
                text = "Hoàn thành chỉnh sửa",
                modifier = Modifier.padding(5.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ButtonDelete(
    navController: NavController,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val debouncedOnClick = remember { debounce<Unit>(scope = scope) { onClick() } }

    Button(
        onClick = { debouncedOnClick(Unit) },
        enabled = !isLoading,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp, bottom = 30.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Text(
                text = "Xóa phòng",
                modifier = Modifier.padding(5.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun BodyEdit(
    imageUris: List<Uri>,
    description: TextFieldValue,
    onDescChange: (TextFieldValue) -> Unit,
    price: TextFieldValue,
    onPriceChange: (TextFieldValue) -> Unit,
    address: TextFieldValue,
    onAddressChange: (TextFieldValue) -> Unit,
    numberOfPeople: TextFieldValue,
    onPeopleChange: (TextFieldValue) -> Unit,
    internetFee: TextFieldValue,
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
    telephoneNumber: TextFieldValue,
    onTelephoneNumber: (TextFieldValue) -> Unit,
    onAddImageClick: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            text = "Thay đổi ảnh",
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
                    model = ImageRequest.Builder(context)
                        .data(uri)
                        .crossfade(true)
                        .size(100, 100)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .build(),
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
        SectionText("Tiền mạng /tháng")
        TextFieldOutlined(internetFee, onServiceChange)
        SectionText("Phí vệ sinh")
        TextFieldOutlined(cleaningFee, onCleaningChange)
        SectionText("Giá điện")
        TextFieldOutlined(electricFee, onElectricChange)
        SectionText("Giá nước")
        TextFieldOutlined(waterFee, onWaterChange)
        SectionText("Phí bảo vệ")
        TextFieldOutlined(protectFee, onProtectChange)
        SectionText("Số liên hệ")
        TextFieldOutlined(telephoneNumber, onTelephoneNumber)
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