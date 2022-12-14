package com.example.refrigerator

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.refrigerator.databinding.FragmentTab2Binding
import com.example.refrigerator.databinding.RecipeItemLayoutBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class Tab2Fragment: Fragment() {

    lateinit var binding: FragmentTab2Binding
    val recipeList: ArrayList<RecipeData> = arrayListOf()
    var helper: ItemTouchHelper? = null
    var ingredientName: java.util.ArrayList<String> = java.util.ArrayList()
    var ingredientAmount: java.util.ArrayList<String> = java.util.ArrayList()
    var ingredientTime: java.util.ArrayList<String> = java.util.ArrayList()
    var arrayList: java.util.ArrayList<Any> = java.util.ArrayList()
    var recipes: ArrayList<RecipeData> = arrayListOf()

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentTab2Binding.inflate(inflater, container, false)


        recipeList.apply{
            add(RecipeData("김치찌개", arrayListOf(
                needData("김치","300g",0),
                needData("돼지고기","100g",0),
                needData("대파","30g",0),
                needData("마늘","20g",0)
            ),R.drawable.pic5_kimchi,0,0))
            add(RecipeData("된장찌개", arrayListOf(
                needData("된장","300g",0),
                needData("양파","300g",0),
                needData("소금","5g",0),
                needData("마늘","20g",0),
                needData("청양고추","40g",0),
                needData("설탕","5g",0),
                needData("애호박","100g",0),
                needData("감자","50g",0),
            ),R.drawable.soup,0,0))
            add(RecipeData("명란계란찜", arrayListOf(
                needData("명란젓","50g",0),
                needData("파","30g",0),
                needData("계란","3",0),
                needData("소금","5",0)
            ),R.drawable.pic8_seafood,0,0))
            add(RecipeData("어묵탕", arrayListOf(
                needData("어묵","100g",0),
                needData("무","100g",0),
                needData("양파","100g",0),
                needData("소금","5g",0),
                needData("후추","5g",0),
                needData("마늘","20g",0),
                needData("청양고추","20g",0)
            ),R.drawable.hot_soup,0,0))
            add(RecipeData("오징어볶음", arrayListOf(
                needData("오징어","600g",0),
                needData("양배추","100g",0),
                needData("당근","200g",0),
                needData("양파","150g",0),
                needData("파","100g",0),
                needData("마늘","20g",0),
                needData("청양고추","20g",0),
                needData("설탕","10g",0)
            ),R.drawable.lobster,0,0))
            add(RecipeData("닭가슴살전", arrayListOf(
                needData("닭가슴살","50g",0),
                needData("양파","100g",0),
                needData("소금","5g",0),
                needData("달걀","3",0),
                needData("파","20g",0)
            ),R.drawable.chicken_leg,0,0))
            add(RecipeData("미역국", arrayListOf(
                needData("미역","20g",0),
                needData("소고기","100g",0),
                needData("소금","5g",0),
                needData("마늘","20g",0)
            ),R.drawable.soup,0,0))
            add(RecipeData("두부김치", arrayListOf(
                needData("두부","100g",0),
                needData("김치","100g",0),
                needData("돼지고기","250g",0),
                needData("양파","100g",0),
                needData("당근","20g",0),
                needData("대파","20g",0),
                needData("청양고추","20g",0),
                needData("마늘","10g",0)
            ),R.drawable.pic5_kimchi,0,0))
            add(RecipeData("부추계란말이", arrayListOf(
                needData("부추","100g",0),
                needData("계란","4",0),
                needData("소금","5g",0)
            ),R.drawable.salad,0,0))
            add(RecipeData("수육", arrayListOf(
                needData("돼지고기","600g",0),
                needData("마늘","50g",0),
                needData("된장","100g",0),
                needData("후추","100g",0)
            ),R.drawable.chop,0,0))
            add(RecipeData("부대찌개", arrayListOf(
                needData("돼지고기","200g",0),
                needData("마늘","100g",0),
                needData("김치","50g",0),
                needData("양파","50g",0),
                needData("후추","50g",0),
                needData("대파","20g",0),
                needData("소시지","50g",0)
            ),R.drawable.hot_soup,0,0))
            add(RecipeData("돼지고기숙주볶음", arrayListOf(
                needData("돼지고기","180g",0),
                needData("숙주","210g",0),
                needData("대파","50g",0),
                needData("마늘","50g",0)
            ),R.drawable.chop,0,0))
        }

        var firestore: FirebaseFirestore? = null
        var uid: String? = null
        uid = FirebaseAuth.getInstance().currentUser?.uid
        firestore = FirebaseFirestore.getInstance()

        val adapter = RecipeRVAdapter(recipes)

//        for(i in 0 until recipeList.size) {
//            firestore.collection("recipes").document(i.toString()).set(recipeList[i])
//        }
            //예시 초기화용
//        recipeList.sortBy { it.name }
//        for(i in 0 until recipeList.size){
//            firestore!!.collection("recipes").document(i.toString())
//                .set(recipeList[i])
//        }

            firestore?.collection("recipes")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // ArrayList 비워줌
                recipes.clear()

                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(RecipeData::class.java)
                    recipes.add(item!!)
                }
                recipes.sortBy { it.name }
                adapter.notifyDataSetChanged()
            }

        binding.tab2RV.layoutManager = LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false)


        Log.d("Dd",recipes.size.toString())
        //binding.mainFeed.adapter = Postadapter
        binding.tab2RV.adapter = adapter

        binding.tab2RV.addItemDecoration(RVDecoration(40,1))


        //각 아이템을 클릭했을 때
        adapter.setMyItemClickListener(object : RecipeRVAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                if(recipes[position].state == 0) {
                    recipes[position].click = 1
                    recipes[position].state = 1
                }else if (recipes[position].state ==1){
                    recipes[position].click = 1
                    recipes[position].state = 0
                }

                adapter.notifyItemChanged(position)
            }override fun onLongClick(position: Int) {
                recipes.removeAt(position)

                recipes.sortBy { it.name }

                firestore!!.collection("recipes").document(position.toString())
                    .delete()
                for (i in 0 until recipes.size) {
                    firestore!!.collection("recipes").document(i.toString())
                        .set(recipes[i])
                }
                firestore!!.collection("recipes").document(recipes.size.toString())
                    .delete()
                adapter.notifyDataSetChanged()

            }
        })


        adapter.notifyDataSetChanged()


        //아이템 스와이프
        val itemTouchHelperCallback = ItemTouchHelperCallback(adapter)

        // ItemTouchHelper의 생성자로 ItemTouchHelper.Callback 객체 셋팅
        val helper = ItemTouchHelper(itemTouchHelperCallback)
        // RecyclerView에 ItemTouchHelper 연결
        //helper.attachToRecyclerView(binding.tab1RV)

        return binding.root
    }
            fun toTimeStamp(st: String): Date {
        var preConverted = st
        var _seconds = (preConverted.substring(18, 28)).toLong(); // 1621176915
        var _nanoseconds = (preConverted.substring(42, preConverted.lastIndexOf(')'))).toInt(); // 276147000
        var postConverted = Timestamp(_seconds, _nanoseconds);
        return postConverted.toDate()
    }
}
