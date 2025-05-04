package com.example.projectandroidapp_findingroom

data class Room(
    val urlImage: List<String> = emptyList(),
    val price: Int = 0,
    val address: String = "",
    val numberOfPeople: Int = 0,
    //Detail
    val serviceFee: Int = 0,
    val cleaningFee: Int = 0,
    val electricFee: Int = 0,
    val waterFee: Int = 0,
    val protectFee: Int = 0,
    //Nội thất
    val hasBasicInterior: Boolean = true,
    val hasSofa: Boolean = false,
    val hasRefrigerator: Boolean = false,
    val hasHotWater: Boolean = false,
    val hasWaredrobe: Boolean = false,
    val hasBed: Boolean = false,
    //Author
    val author: String = "",
    val description: String = "",
    //Trạng thái còn/hết
    val state: Boolean = true,
    val id: Int = 0,
    //Else
    val elseInterior: String = "",
)
