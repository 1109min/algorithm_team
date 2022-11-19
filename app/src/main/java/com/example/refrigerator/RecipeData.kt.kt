package com.example.refrigerator

import java.io.Serializable

data class RecipeData(
    var name : String,
    var ingredient : ArrayList<needData>,
    var pic : Int,
    var click : Int,
    var state : Int
) : Serializable