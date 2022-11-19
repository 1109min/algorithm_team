package com.example.refrigerator

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

class RecipeData(
    var name : String,

    var ingredient : ArrayList<needData>,

    var pic : Int,

    var click : Int,

    var state : Int

) : Serializable