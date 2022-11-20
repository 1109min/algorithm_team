package com.example.refrigerator

import java.io.Serializable

class ResultData(
        var name: String ="",
        var ingredients : ArrayList<needData> = arrayListOf(needData("","",0)),
        var date: String="",
        var pic: Int=0,
        var star: Int=0
        ):Serializable