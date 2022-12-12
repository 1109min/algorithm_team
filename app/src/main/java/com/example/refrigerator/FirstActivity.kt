package com.example.refrigerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.refrigerator.databinding.ActivityFirstBinding
import com.example.refrigerator.databinding.ActivityMakeBinding

class FirstActivity : AppCompatActivity() {
    private val binding: ActivityFirstBinding by lazy {
        ActivityFirstBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)





    }
}