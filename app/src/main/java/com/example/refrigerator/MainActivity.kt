package com.example.refrigerator

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.refrigerator.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private val viewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var fab_main: FloatingActionButton? = null
    private  var fab_sub1:FloatingActionButton? = null
    private  var fab_sub2:FloatingActionButton? = null
    private var fab_open: Animation? = null
    private  var fab_close:Animation? = null
    private var isFabOpen = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        var mContext = getApplicationContext()


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

        fab_open = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(mContext, R.anim.fab_close);
        fab_main = viewBinding.mainBtn
        fab_sub1 = viewBinding.ingredientPlusBtn
        fab_sub2 = viewBinding.recipePlusBtn



        fab_main!!.setOnClickListener{
            toggleFab()
        }

        fab_sub1!!.setOnClickListener{
            toggleFab()
        }

        fab_sub2!!.setOnClickListener {
            toggleFab()
        }



    }
    private fun toggleFab() {
        if (isFabOpen) {
            fab_main!!.setImageResource(R.drawable.ic_baseline_add_24)
            fab_sub1!!.startAnimation(fab_close)
            fab_sub2!!.startAnimation(fab_close)
            fab_sub1!!.isClickable = false
            fab_sub2!!.isClickable = false
            isFabOpen = false
        } else {
            fab_main!!.setImageResource(R.drawable.ic_baseline_close_24)
            fab_sub1!!.startAnimation(fab_open)
            fab_sub2!!.startAnimation(fab_open)
            fab_sub1!!.isClickable = true
            fab_sub2!!.isClickable = true
            isFabOpen = true
        }
    }
}