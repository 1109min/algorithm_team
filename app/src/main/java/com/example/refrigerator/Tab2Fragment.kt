package com.example.refrigerator

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.refrigerator.databinding.FragmentTab2Binding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList


class Tab2Fragment: Fragment() {

    lateinit var binding: FragmentTab2Binding
    val recipeList: ArrayList<RecipeData> = arrayListOf()
    var helper: ItemTouchHelper? = null
    var ingredientName: java.util.ArrayList<String> = java.util.ArrayList()
    var ingredientAmount: java.util.ArrayList<String> = java.util.ArrayList()
    var ingredientTime: java.util.ArrayList<String> = java.util.ArrayList()
    var arrayList: java.util.ArrayList<Any> = java.util.ArrayList()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentTab2Binding.inflate(inflater, container, false)

        binding.tab2RV.layoutManager = LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false)

        val adapter = RecipeRVAdapter(recipeList)

        //binding.mainFeed.adapter = Postadapter
        binding.tab2RV.adapter = adapter
        binding.tab2RV.addItemDecoration(RVDecoration(50,1))


        recipeList.apply{
            add(RecipeData("김치찌개","김치",100))
            add(RecipeData("순두부찌개","순두부",200))
            add(RecipeData("된장찌개","된장",150))
            add(RecipeData("김치찌개","김치",100))
            add(RecipeData("순두부찌개","순두부",200))
            add(RecipeData("된장찌개","된장",150))
            add(RecipeData("김치찌개","김치",100))
            add(RecipeData("순두부찌개","순두부",200))
            add(RecipeData("된장찌개","된장",150))
        }
        adapter.notifyDataSetChanged()
//firebase 연동
//        var firestore: FirebaseFirestore? = null
//        var uid: String? = null
//        uid = FirebaseAuth.getInstance().currentUser?.uid
//        val format = SimpleDateFormat("yyyy-MM-dd")
//
//
//        firestore = FirebaseFirestore.getInstance();
//        firestore.collection("sourcefile").document("ingredients")
//            .addSnapshotListener { value, error ->
//                Log.d("event", value.toString())
//                arrayList.clear()
//                arrayList.addAll((value?.data?.get("ingredient") as java.util.ArrayList<*>))
//
//                //배열에 저장
//                for (i in 0 until arrayList.size) {
//                    try {
//                        if (i % 3 == 0){
//                            ingredientName.add(arrayList[i] as String)
//                        }
//                        else if (i % 3 == 1) {
//                            ingredientTime.add(format.format(toTimeStamp(arrayList[i].toString())))
//                        }
//                        else if (i % 3 == 2){
//                            ingredientAmount.add(arrayList[i] as String)
//                        }
//
//                    } catch (e: Exception) {
//                        Log.d("error:$i", e.toString())
//                    }
//
//                }
//
//                for (i in 0 until ingredientName.size) {
//
//                    ingredientList.add(
//                        IngredientData(
//                            ingredientName[i],
//                            ingredientAmount[i],
//                            ingredientTime[i]
//                        )
//                    )
//                }
//                var byYear = Comparator.comparing { obj: IngredientData -> obj.dateString.split("-")[0]}
//                var byMonth = Comparator.comparing { obj: IngredientData -> obj.dateString.split("-")[1]}
//                var byday = Comparator.comparing { obj: IngredientData -> obj.dateString.split("-")[2]}
//                ingredientList.sortWith(byYear.thenComparing(byMonth.thenComparing(byday)))
//
//                adapter.notifyDataSetChanged()
//            }


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
