package com.example.firestore

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectandroidapp_findingroom.Room
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// Định nghĩa sealed class cho trạng thái thêm phòng
sealed class AddRoomState {
    object Idle : AddRoomState()
    data class Success(val roomId: String) : AddRoomState()
    data class Error(val message: String) : AddRoomState()
}

// Định nghĩa sealed class cho trạng thái xóa phòng
sealed class DeleteRoomState {
    object Idle : DeleteRoomState()
    object Loading : DeleteRoomState()
    object Success : DeleteRoomState()
    data class Error(val message: String) : DeleteRoomState()
}

// Định nghĩa sealed class cho trạng thái cập nhật phòng
sealed class UpdateRoomState {
    object Idle : UpdateRoomState()
    data class Success(val roomId: String) : UpdateRoomState()
    data class Error(val message: String) : UpdateRoomState()
}

class RoomViewModel : ViewModel() {
    private val _roomList = MutableStateFlow<List<Room>>(emptyList())
    val roomList = _roomList.asStateFlow()

    // StateFlow cho các trạng thái
    private val _addRoomState = MutableStateFlow<AddRoomState>(AddRoomState.Idle)
    val addRoomState = _addRoomState.asStateFlow()

    private val _deleteRoomState = MutableStateFlow<DeleteRoomState>(DeleteRoomState.Idle)
    val deleteRoomState = _deleteRoomState.asStateFlow()

    private val _updateRoomState = MutableStateFlow<UpdateRoomState>(UpdateRoomState.Idle)
    val updateRoomState = _updateRoomState.asStateFlow()

    private val db = FirebaseFirestore.getInstance()

    init {
        getRoomList()
    }

    // Lấy danh sách phòng từ Firestore
    fun getRoomList() {
        db.collection("rooms")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("RoomViewModel", "Firestore error: ${error.message}", error)
                    return@addSnapshotListener
                }

                if (value != null) {
                    val rooms = value.toObjects(Room::class.java).map { room ->
                        room.id = value.documents.find { it.toObject(Room::class.java) == room }?.id ?: ""
                        room
                    }
                    _roomList.value = rooms
                    Log.d("RoomViewModel", "Fetched ${rooms.size} rooms")
                }
            }
    }

    // Thêm phòng mới
    fun addRoom(room: Room) {
        viewModelScope.launch {
            try {
                val documentReference = db.collection("rooms").add(room).await()
                val roomId = documentReference.id
                // Cập nhật roomId vào tài liệu
                documentReference.update("id", roomId).await()
                Log.d("RoomViewModel", "Room added with ID: $roomId")
                _addRoomState.value = AddRoomState.Success(roomId)
            } catch (e: Exception) {
                Log.e("RoomViewModel", "Error adding room: ${e.message}", e)
                _addRoomState.value = AddRoomState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

    // Xóa phòng
    fun deleteRoom(roomId: String) {
        viewModelScope.launch {
            _deleteRoomState.value = DeleteRoomState.Loading
            try {
                db.collection("rooms").document(roomId).delete().await()
                Log.d("RoomViewModel", "Room deleted with ID: $roomId")
                _deleteRoomState.value = DeleteRoomState.Success
            } catch (e: Exception) {
                Log.e("RoomViewModel", "Error deleting room: ${e.message}", e)
                _deleteRoomState.value = DeleteRoomState.Error(e.message ?: "Lỗi khi xóa phòng")
            }
        }
    }

    // Cập nhật phòng
    fun updateRoom(roomId: String, updatedRoom: Room) {
        viewModelScope.launch {
            try {
                updatedRoom.id = roomId
                db.collection("rooms").document(roomId).set(updatedRoom).await()
                Log.d("RoomViewModel", "Room updated with ID: $roomId")
                _updateRoomState.value = UpdateRoomState.Success(roomId)
            } catch (e: Exception) {
                Log.e("RoomViewModel", "Error updating room: ${e.message}", e)
                _updateRoomState.value = UpdateRoomState.Error(e.message ?: "Lỗi khi cập nhật phòng")
            }
        }
    }

    // Reset trạng thái
    fun resetAddRoomState() {
        _addRoomState.value = AddRoomState.Idle
    }

    fun resetDeleteRoomState() {
        _deleteRoomState.value = DeleteRoomState.Idle
    }

    fun resetUpdateRoomState() {
        _updateRoomState.value = UpdateRoomState.Idle
    }
}