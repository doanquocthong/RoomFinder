package com.example.projectandroidapp_findingroom

data class Room(
    val urlImage: List<String> = emptyList(),
    val price: String = "",
    val address: String = "",
    val numberOfPeople: String = "",
    //Detail
    val internetFee: String = "",
    val cleaningFee: String = "",
    val electricFee: String = "",
    val waterFee: String = "",
    val protectFee: String = "",
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
    val id: String = "0", //1 2 3
    //Else
    val elseInterior: String = "",
    val telephoneNumber: String = ""
)
