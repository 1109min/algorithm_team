package com.example.refrigerator

data class IngredientData(
    var name : String = "",
    var amount : String = "",
    var dateString : String = "",
    var pic : Int = 0,
    var late : Int = 0
):java.io.Serializable