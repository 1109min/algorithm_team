package com.example.refrigerator

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.refrigerator.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    private val viewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(viewBinding.containerFragment.id,HomeFragment()) //add를 하면 겹친다
            .commitAllowingStateLoss() //그냥 commit하면 에러날 수도 있음

        viewBinding.navBottom.run{
            setOnItemSelectedListener {
                when(it.itemId){
                    R.id.menu_home -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.containerFragment.id,HomeFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_list -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.containerFragment.id,ListFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_settings -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.containerFragment.id, SettingFragment())
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            selectedItemId = R.id.menu_home
        }

        //재료 추가 버튼
        viewBinding.mainBtn.setOnClickListener {

        }
    }
}