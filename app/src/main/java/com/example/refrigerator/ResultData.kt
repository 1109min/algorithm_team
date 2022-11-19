package com.example.refrigerator

import java.io.Serializable

class ResultData(
        var name: String?,
        var ingredient: String,
        var amount: Int,
        var date: String,
        var pic: Int,
        var star: Int
        ):Serializable