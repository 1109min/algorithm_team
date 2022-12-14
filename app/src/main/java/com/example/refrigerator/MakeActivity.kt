package com.example.refrigerator

import Leftover_Food
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import com.example.refrigerator.databinding.ActivityMakeBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp
import java.text.SimpleDateFormat

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

    private var btn_in: Animation? = null

    var firestore: FirebaseFirestore? = null

    var result_menu : ResultData = ResultData()
    var base_recipe : RecipeData = RecipeData()

    var origin_recipes : ArrayList<RecipeData> = arrayListOf()
    var origin_ingredients : ArrayList<IngredientData> = arrayListOf()

    var resData = ArrayList<RecipeData>()

    val handler2: Handler = object : Handler(Looper.getMainLooper()) {
        @RequiresApi(Build.VERSION_CODES.N)
        override fun handleMessage(msg: Message) {
            if (msg.what == 1) {

                Log.d("rrrrrrrrrr1",origin_ingredients.size.toString())
                Log.d("rrrrrrrrrr2",origin_recipes.size.toString())

                val result = Leftover_Food(origin_ingredients, origin_recipes)


                resData = result.get_Leftover_Food()

                if(resData.size <=0){
                    binding.indicator.visibility = GONE

                }else if(resData.size>0){

                    var size = resData.size

                    base_recipe = resData[0]

                    print()

                    binding.menuName.text = base_recipe.name

                    var current = Timestamp(System.currentTimeMillis())
                    var sdf = SimpleDateFormat("yyyy-MM-dd")
                    var currentdate = sdf.format(current)

                    result_menu.date = currentdate
                    result_menu.star = 0
                    Log.d("rrrrrrrrrr3",resData[0].name)
                    var index:Int = 0;

                    binding.beforeBtn.setOnClickListener {
                        index --
                        if(index <0){
                            index = size -1
                        }
                        binding.indicator.text = (index+1).toString() + "/" + size.toString()

                        base_recipe = resData[index];
                        print()
                    }
                    binding.nextBtn.setOnClickListener {
                        index ++
                        if(index >size-1){
                            index = 0
                        }
                        binding.indicator.text = (index+1).toString() + "/" + size.toString()

                        base_recipe = resData[index];
                        print()
                    }
                    binding.checkBtn.setOnClickListener {
                        Log.d("??????",index.toString()+ resData[index].name)
                        base_recipe = resData[index]

                        result_menu.name = base_recipe.name
                        result_menu.ingredients = base_recipe.ingredient
                        result_menu.pic = base_recipe.pic

                        var current = Timestamp(System.currentTimeMillis())
                        var sdf = SimpleDateFormat("yyyy-MM-dd")
                        var currentdate = sdf.format(current)

                        result_menu.date = currentdate
                        result_menu.star = 0

                        firestore!!.collection("currents").document("0").set(result_menu)
                        firestore!!.collection("currents1").document("0").set(result_menu)

                        finish()
                    }
                }
            }
        }
    }

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
        btn_in = AnimationUtils.loadAnimation(this,R.anim.menu_info_in)

        binding.dishTopContainer.visibility = GONE
        binding.dishContainer.visibility = GONE
        binding.menuInfo.visibility = GONE
        binding.makeLoad.visibility = GONE
        binding.btnContainer.visibility=GONE


        val handler = Handler(mainLooper)
        var started = true
        var progress = -1;
        Thread(){
            while(started){
                handler.post{
                    when(progress){
                        0->{

                            // AnimatorSet ???????????? ??????
                            val animatorSet = AnimatorSet()
                            // ObjectAnimator ???????????? ??????
                            val objectAnimator1 = ObjectAnimator.ofFloat(binding.first, View.ROTATION, 0f, -20f).setDuration(50)
                            val objectAnimator2 = ObjectAnimator.ofFloat(binding.first, View.ROTATION, -20f, 20f).setDuration(100)
                            val objectAnimator3 = ObjectAnimator.ofFloat(binding.first, View.ROTATION, 20f, -20f).setDuration(100)
                            val objectAnimator4 = ObjectAnimator.ofFloat(binding.first, View.ROTATION, -20f, 0f).setDuration(50)

                            // ??????????????? ????????? ??? ????????? AnimatorSet??? ObjectAnimator ??????
                            animatorSet.playSequentially(objectAnimator1, objectAnimator2, objectAnimator3, objectAnimator4)

                            // ??????????????? ??????
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

                            binding.menuInfo.visibility = VISIBLE
                            binding.menuInfo.startAnimation(menu_info_in)
                        }
                        13->{
                            binding.btnContainer.visibility = VISIBLE
                            binding.btnContainer.startAnimation(btn_in)
                        }
                        14->{
                            started = false
                        }
                    }
                    progress ++
                }
                Thread.sleep(500)
            }
        }.start()

        //?????? ????????????
        var in_check : Boolean = false
        firestore = FirebaseFirestore.getInstance()

        firestore?.collection("ingredient")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            // ArrayList ?????????
            origin_ingredients.clear()
            for (snapshot in querySnapshot!!.documents) {
                var item = snapshot.toObject(IngredientData::class.java)
                origin_ingredients.add(item!!)
            }
            Log.d("aaddd",origin_ingredients.size.toString())
            in_check = true
        }

        //?????? ????????????
        firestore?.collection("recipes")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            // ArrayList ?????????
            origin_recipes.clear()
            for (snapshot in querySnapshot!!.documents) {
                var item = snapshot.toObject(RecipeData::class.java)
                origin_recipes.add(item!!)
            }
            Log.d("aa",origin_recipes.size.toString())
            handler2.sendEmptyMessage(1)
        }
    } //create ???

    fun print(){
        result_menu.name = base_recipe.name
        result_menu.ingredients = base_recipe.ingredient
        result_menu.pic = base_recipe.pic

        var current = Timestamp(System.currentTimeMillis())
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var currentdate = sdf.format(current)

        result_menu.date = currentdate
        result_menu.star = 0

        binding.menuName.text = base_recipe.name

        var info:String = ""
        for(i in 0 until base_recipe.ingredient.size-1) {

            var new_info =
                base_recipe.ingredient[i].name + " : " + base_recipe.ingredient[i].amount


            if(i%2==0){
                new_info += "\n"
            }else{
                new_info += "   "
            }
            info += new_info
        }
        var new_info =
            base_recipe.ingredient[base_recipe.ingredient.size-1].name + " : " + base_recipe.ingredient[base_recipe.ingredient.size-1].amount

        info += new_info
        binding.menuIngredient.text = info
        binding.makeLoad.setImageResource(base_recipe.pic)
    }
}




