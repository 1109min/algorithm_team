package com.example.refrigerator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.refrigerator.databinding.FragmentTab1Binding


class Tab1Fragment: Fragment() {

    lateinit var binding: FragmentTab1Binding
    val ingredientList: ArrayList<IngredientData> = arrayListOf()
    var helper: ItemTouchHelper? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentTab1Binding.inflate(inflater, container, false)

        binding.tab1RV.layoutManager = LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false)

        val adapter = IngredientRVAdapter(ingredientList)


        //binding.mainFeed.adapter = Postadapter
        binding.tab1RV.adapter = adapter
        binding.tab1RV.addItemDecoration(RVDecoration(50,1))


        ingredientList.apply{
            add(IngredientData("김치",200,"11월 30일"))
            add(IngredientData("깍두기",100,"12월 3일"))
            add(IngredientData("우유",1000,"12월 14일"))
            add(IngredientData("김치",200,"11월 30일"))
            add(IngredientData("깍두기",100,"12월 3일"))
            add(IngredientData("우유",1000,"12월 14일"))
            add(IngredientData("김치",200,"11월 30일"))
            add(IngredientData("깍두기",100,"12월 3일"))
            add(IngredientData("우유",1000,"12월 14일"))
            add(IngredientData("김치",200,"11월 30일"))
            add(IngredientData("깍두기",100,"12월 3일"))
            add(IngredientData("우유",1000,"12월 14일"))

        }
        adapter.notifyDataSetChanged()

        val itemTouchHelperCallback = ItemTouchHelperCallback(adapter)
        helper = ItemTouchHelper(itemTouchHelperCallback)
        helper!!.attachToRecyclerView(binding.tab1RV)

//            Postadapter.addUserItems(PostData(title_memo, text_memo))
        return binding.root
    }
}
