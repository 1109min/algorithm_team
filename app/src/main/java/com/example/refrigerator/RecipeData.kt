package com.example.refrigerator

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class RecipeData(
    var name : String ="",

    var ingredient : ArrayList<needData> = arrayListOf(needData("","",0)),

    var pic : Int = 0,

    var click : Int = 0,

    var state : Int = 0

):Serializable