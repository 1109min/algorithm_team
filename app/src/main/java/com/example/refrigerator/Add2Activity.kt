package com.example.refrigerator

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.refrigerator.databinding.ActivityAdd2Binding
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList

class Add2Activity : AppCompatActivity() {

    private val binding: ActivityAdd2Binding by lazy {
        ActivityAdd2Binding.inflate(layoutInflater)
    }
    val dataList: ArrayList<IngredientData> = arrayListOf()
    var firestore: FirebaseFirestore? = null
    private var fab_open: Animation? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var intent = Intent(this, MainActivity::class.java)

        binding.addRv.layoutManager = LinearLayoutManager(this,
            RecyclerView.VERTICAL,false)
        val anim = AnimationUtils.loadLayoutAnimation(this, R.anim.anim_slide)
        binding.addRv.layoutAnimation = anim
        binding.addRv.scheduleLayoutAnimation()

        val adapter = AddingredientRVAdapter(dataList)

        //binding.mainFeed.adapter = Postadapter
        binding.addRv.adapter = adapter
//아이템 스와이프
        val itemTouchHelperCallback = ItemTouchHelperCallback(adapter)

        // ItemTouchHelper의 생성자로 ItemTouchHelper.Callback 객체 셋팅
        val helper = ItemTouchHelper(itemTouchHelperCallback)
        // RecyclerView에 ItemTouchHelper 연결

        helper.attachToRecyclerView(binding.addRv)

        binding.addRv.addItemDecoration(RVDecoration(20,1))
        adapter.setMyItemClickListener(object : AddingredientRVAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {


            }override fun onLongClick(position: Int){

            }
        })

        var data : ArrayList<IngredientData> = arrayListOf()


        var inname = binding.ingredientNameET
        var inamount = binding.ingredientAmountET
        var indate = binding.ingredientDateET

        firestore = FirebaseFirestore.getInstance()
        var origin_ingredients : ArrayList<IngredientData> = arrayListOf()

        //읽어오기
        firestore?.collection("ingredient")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            // ArrayList 비워줌
            origin_ingredients.clear()

            for (snapshot in querySnapshot!!.documents) {
                var item = snapshot.toObject(IngredientData::class.java)
                origin_ingredients.add(item!!)
            }
        }



        binding.menuAddBtn.setOnClickListener {
            intent.putExtra("ingredient",dataList)
            intent.putExtra("in_check","1")
            intent.putExtra("index",origin_ingredients.size)
//
//            origin_ingredients.add(IngredientData("김치", "1000g", "2022-11-31", 0, 0))
//            origin_ingredients.add(IngredientData("돼지고기", "250g", "2022-11-25", 0, 0))
//            origin_ingredients.add(IngredientData("마늘", "120g", "2022-12-05", 0, 0))
//            origin_ingredients.add(IngredientData("계란", "8", "2022-11-28", 0, 0))
//            origin_ingredients.add(IngredientData("당근", "150g", "2022-11-29", 0, 0))
//            origin_ingredients.add(IngredientData("어묵", "250g", "2022-12-22", 0, 0))
//            origin_ingredients.add(IngredientData("설탕", "10g", "2023-01-30", 0, 0))
//            origin_ingredients.add(IngredientData("청양고추", "20g", "2023-02-11", 0, 0))
//            origin_ingredients.add(IngredientData("소고기", "200g", "2022-11-30", 0, 0))
//            origin_ingredients.add(IngredientData("부추", "150g", "2022-12-10", 0, 0))
//            origin_ingredients.add(IngredientData("숙주", "210g", "2022-12-29", 0, 0))
//            origin_ingredients.add(IngredientData("소금", "10g", "2022-12-19", 0, 0))
//            origin_ingredients.add(IngredientData("파", "45g", "2022-11-23", 0, 0))
//            origin_ingredients.add(IngredientData("대파", "80g", "2022-12-02", 0, 0))
//            origin_ingredients.add(IngredientData("양배추", "100g", "2022-12-06", 0, 0))
//            origin_ingredients.add(IngredientData("후추", "100g", "2023-01-24", 0, 0))
//            origin_ingredients.add(IngredientData("감자", "80g", "2022-11-31", 0, 0))
//            origin_ingredients.add(IngredientData("미역", "80g", "2022-12-11", 0, 0))


//
//            var ingDat = ArrayList<IngredientData>()
//            ingDat.add(IngredientData("김치", "1000g", "2022-11-31", 0, 0))
//            ingDat.add(IngredientData("돼지고기", "250g", "2022-11-25", 0, 0))
//            ingDat.add(IngredientData("마늘", "120g", "2022-12-05", 0, 0))
//            ingDat.add(IngredientData("계란", "8", "2022-11-28", 0, 0))
//            ingDat.add(IngredientData("당근", "150g", "2022-11-29", 0, 0))
//            ingDat.add(IngredientData("어묵", "250g", "2022-12-22", 0, 0))
//            ingDat.add(IngredientData("설탕", "10g", "2023-01-30", 0, 0))
//            ingDat.add(IngredientData("청양고추", "20g", "2023-02-11", 0, 0))
//            ingDat.add(IngredientData("소고기", "200g", "2022-11-30", 0, 0))
//            ingDat.add(IngredientData("부추", "150g", "2022-12-10", 0, 0))
//            ingDat.add(IngredientData("숙주", "210g", "2022-12-29", 0, 0))
//            ingDat.add(IngredientData("소금", "10g", "2022-12-19", 0, 0))
//            ingDat.add(IngredientData("파", "45g", "2022-11-23", 0, 0))
//            ingDat.add(IngredientData("대파", "80g", "2022-12-02", 0, 0))
//            ingDat.add(IngredientData("양배추", "100g", "2022-12-06", 0, 0))
//            ingDat.add(IngredientData("후추", "100g", "2023-01-24", 0, 0))
//            ingDat.add(IngredientData("감자", "80g", "2022-11-31", 0, 0))
//            ingDat.add(IngredientData("미역", "80g", "2022-12-11", 0, 0))
//
//            for(i in 0 until ingDat.size) {
//                firestore!!.collection("ingredient_test").document(i.toString())
//                    .set(ingDat[i])
//                Log.d("wow",ingDat.size.toString()+" "+ingDat[i].toString()+"kjkjk "+i)
//            }

            for(i in 0 until dataList.size) {
                origin_ingredients.add(dataList[i])
            }
            var byYear = Comparator.comparing { obj: IngredientData -> obj.dateString.split("-")[0]}
                var byMonth = Comparator.comparing { obj: IngredientData -> obj.dateString.split("-")[1]}
                var byday = Comparator.comparing { obj: IngredientData -> obj.dateString.split("-")[2]}

            origin_ingredients.sortWith(byYear.thenComparing(byMonth.thenComparing(byday)))

            //쓰기
            for(i in 0 until origin_ingredients.size) {
                firestore!!.collection("ingredient").document(i.toString())
                    .set(origin_ingredients[i])
                Log.d("wow",origin_ingredients.size.toString()+" "+origin_ingredients[i].toString()+"kjkjk "+i)
            }

            setResult(RESULT_OK, intent)
            finish()
        }
        binding.addBtn.setOnClickListener {
            if (inname.equals("") || inamount.equals("")) {

            } else {
                dataList.add(IngredientData(inname.text.toString(),inamount.text.toString(),indate.text.toString(),0,0))

                inname.setText("")
                inamount.setText("")
                indate.setText("")

                adapter.notifyDataSetChanged()

            }
        }



        setContentView(binding.root)
    }

    override fun onBackPressed() {
        setResult(RESULT_OK, intent)
        finish()
        super.onBackPressed()
    }
}

