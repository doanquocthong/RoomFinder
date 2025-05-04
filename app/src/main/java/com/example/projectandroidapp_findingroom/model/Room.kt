package com.example.projectandroidapp_findingroom

data class Room(
    val urlImage: List<String> = emptyList(),
    val price: Int = 0,
    val address: String = "",
    val numberOfPeople: String = "",
    //Detail
    val serviceFee: Int = 0,
    val cleaningFee: Int = 0,
    val electricFee: Int = 0,
    val waterFee: Int = 0,
    val protectFee: Int = 0,
    //Nội thất
    val isInterior: Boolean = true,
    val isSofa: Boolean = true,
    val isRefrigerator: Boolean = true,
    val isFridge: Boolean = true,
    val isWaredrobe: Boolean = true,
    val isBed: Boolean = true,
    //Author
    val author: String = "",
    val description: String = "",
    //Trạng thái còn/hết
    val state: Boolean = false,
    val id: Int = 0,
    //Else
    val elseInterior: String = "",
)
