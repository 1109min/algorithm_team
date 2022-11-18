package com.example.refrigerator

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.renderscript.ScriptGroup.Binding
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.refrigerator.databinding.ActivityMainBinding
import com.example.refrigerator.databinding.ActivityMakeBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.AnimationMode
import org.checkerframework.checker.index.qual.GTENegativeOne

class MakeActivity : AppCompatActivity() {
    private val binding: ActivityMakeBinding by lazy {
        ActivityMakeBinding.inflate(layoutInflater)
    }
    private var anim_up: Animation? = null
    private var anim_down: Animation? = null

    private var dish_up: Animation? = null
    private var dish_down: Animation? = null

    private var anim_in: Animation? = null
    private var anim_out: Animation? = null

    private var menu_up: Animation? = null
    private var menu_info_in: Animation? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)




        anim_up = AnimationUtils.loadAnimation(this, R.anim.move_bottom_up)
        anim_down = AnimationUtils.loadAnimation(this, R.anim.move_bottom_down)
        anim_in = AnimationUtils.loadAnimation(this,R.anim.fab_open)
        anim_out = AnimationUtils.loadAnimation(this,R.anim.fab_close)
        dish_up = AnimationUtils.loadAnimation(this,R.anim.dish_up)
        dish_down = AnimationUtils.loadAnimation(this,R.anim.dish_down)
        menu_up = AnimationUtils.loadAnimation(this,R.anim.menu_up)
        menu_info_in = AnimationUtils.loadAnimation(this,R.anim.menu_info_in)

        binding.dishTopContainer.visibility = GONE
        binding.dishContainer.visibility = GONE
        binding.menuInfo.visibility = GONE
        binding.makeLoad.visibility = GONE

        val handler = Handler(mainLooper)
        var started = true
        var progress = -1;
        Thread(){
            while(started){
                handler.post{
                    when(progress){
                        0->{
                            // AnimatorSet 인스턴스 생성
                            val animatorSet = AnimatorSet()
                            // ObjectAnimator 인스턴스 생성
                            val objectAnimator1 = ObjectAnimator.ofFloat(binding.first, View.ROTATION, 0f, -20f).setDuration(50)
                            val objectAnimator2 = ObjectAnimator.ofFloat(binding.first, View.ROTATION, -20f, 20f).setDuration(100)
                            val objectAnimator3 = ObjectAnimator.ofFloat(binding.first, View.ROTATION, 20f, -20f).setDuration(100)
                            val objectAnimator4 = ObjectAnimator.ofFloat(binding.first, View.ROTATION, -20f, 0f).setDuration(50)

                            // 순차적으로 실행될 수 있도록 AnimatorSet에 ObjectAnimator 추가
                            animatorSet.playSequentially(objectAnimator1, objectAnimator2, objectAnimator3, objectAnimator4)

                            // 애니메이션 시작
                            animatorSet.start()
                        }
                        1->{
                        }
                        2->{
                            binding.dishTopContainer.startAnimation(anim_down)
                            binding.dishContainer.startAnimation(anim_up)
                            binding.dishTopContainer.visibility = VISIBLE
                            binding.dishContainer.visibility = VISIBLE
                        }
                        3->{
                            binding.first.startAnimation(anim_out)
                        }
                        5->{
                            binding.dishTopContainer.startAnimation(dish_up)
                            binding.dishContainer.startAnimation(dish_down)

                        }
                        6->{
                            binding.makeLoad.visibility = VISIBLE
                            binding.makeLoad.startAnimation(anim_in)
                        }
                        8->{
                            binding.makeLoad.startAnimation(menu_up)

                        }
                        11->{
                            binding.menuInfo.startAnimation(menu_info_in)
                            binding.menuInfo.visibility = VISIBLE
                        }
                        12->{
                            started = false
                        }
                    }
                    progress ++
                }
                Thread.sleep(500)
            }
        }.start()

    }

    //메소드 칸
}