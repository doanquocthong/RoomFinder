package com.example.firestore

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.projectandroidapp_findingroom.Room
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// Định nghĩa sealed class để đại diện cho trạng thái thêm phòng
sealed class AddRoomState {
    object Idle : AddRoomState()
    data class Success(val roomId: String) : AddRoomState() // Thêm roomId
    data class Error(val message: String) : AddRoomState()
}

class RoomViewModel : ViewModel() {
    private var _roomList = MutableStateFlow<List<Room>>(emptyList())
    val roomList = _roomList.asStateFlow()
    // StateFlow để theo dõi trạng thái thêm phòng
    private var _addRoomState = MutableStateFlow<AddRoomState>(AddRoomState.Idle)
    val addRoomState = _addRoomState.asStateFlow()

    init {
        getRoomList()
    }

    fun getRoomList() {
        val db = FirebaseFirestore.getInstance()

        db.collection("rooms")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.d("RoomViewModel", "Firestore error: ${error.message}")
                    return@addSnapshotListener
                }

                if (value != null) {
                    _roomList.value = value.toObjects(Room::class.java)
                    Log.d("RoomViewModel", "Firestore value")
                }
            }
    }

    fun addRoom(room: Room) {
        val db = FirebaseFirestore.getInstance()

        db.collection("rooms")
            .add(room)
            .addOnSuccessListener { documentReference ->
                Log.d("RoomViewModel", "Room added with ID: ${documentReference.id}")
                val roomId = documentReference.id
                documentReference.update("id", roomId)
                _addRoomState.value = AddRoomState.Success(roomId)
            }
            .addOnFailureListener { e ->
                Log.w("RoomViewModel", "Error adding room: ${e.message}", e)
                _addRoomState.value = AddRoomState.Error(e.message ?: "Lỗi không xác định")
            }
    }
    fun deleteRoom(roomId: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("rooms")
            .document(roomId)
            .delete()
            .addOnSuccessListener {
                Log.d("RoomViewModel", "Room deleted with ID: $roomId")
            }
            .addOnFailureListener { e ->
                Log.w("RoomViewModel", "Error deleting room: ${e.message}", e)
            }
    }

    fun updateRoom(roomId: String, updatedRoom: Room) {
        val db = FirebaseFirestore.getInstance()

        db.collection("rooms")
            .document(roomId)
            .set(updatedRoom) // Sử dụng set để ghi đè toàn bộ document
            .addOnSuccessListener {
                Log.d("RoomViewModel", "Room updated with ID: $roomId")
            }
            .addOnFailureListener { e ->
                Log.w("RoomViewModel", "Error updating room: ${e.message}", e)
            }
    }


    // Hàm để reset trạng thái thêm phòng
    fun resetAddRoomState() {
        _addRoomState.value = AddRoomState.Idle
    }
}