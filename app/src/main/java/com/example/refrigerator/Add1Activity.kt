package com.example.refrigerator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.refrigerator.databinding.ActivityAdd1Binding
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList

class Add1Activity : AppCompatActivity() {

    private val binding: ActivityAdd1Binding by lazy {
        ActivityAdd1Binding.inflate(layoutInflater)
    }
    val dataList: ArrayList<needData> = arrayListOf()
    var firestore: FirebaseFirestore? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.addRv.layoutManager = LinearLayoutManager(this,
            RecyclerView.VERTICAL,false)

        val adapter = AddMenuRVAdapter(dataList)
        val itemTouchHelperCallback = ItemTouchHelperCallback(adapter)

        // ItemTouchHelper의 생성자로 ItemTouchHelper.Callback 객체 셋팅
        val helper = ItemTouchHelper(itemTouchHelperCallback)
        // RecyclerView에 ItemTouchHelper 연결

        helper.attachToRecyclerView(binding.addRv)
        //binding.mainFeed.adapter = Postadapter
        binding.addRv.adapter = adapter

        binding.addRv.addItemDecoration(RVDecoration(20,1))
        adapter.setMyItemClickListener(object : AddMenuRVAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {


            }override fun onLongClick(position: Int){

            }
        })

        var menudata : ArrayList<RecipeData> = arrayListOf()

        val intent = Intent(this, MainActivity::class.java)

        var inname = binding.ingredientNameET
        var inamount = binding.ingredientAmountET

        firestore = FirebaseFirestore.getInstance()
        var origin_recipes : ArrayList<RecipeData> = arrayListOf()
        firestore?.collection("recipes")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            // ArrayList 비워줌
            origin_recipes.clear()

            for (snapshot in querySnapshot!!.documents) {
                var item = snapshot.toObject(RecipeData::class.java)
                origin_recipes.add(item!!)
            }
        }

        binding.menuAddBtn.setOnClickListener {

            menudata.add(0,RecipeData(binding.menuNameET.text.toString(),dataList,0,0,0))
            origin_recipes.add(menudata[0])

            origin_recipes.sortBy { it.name }
            for(i in 0 until origin_recipes.size) {
                firestore!!.collection("recipes").document(i.toString())
                    .set(origin_recipes[i])
            }


            setResult(RESULT_OK, intent)
            finish()
        }
        binding.addBtn.setOnClickListener {
            if (inname.equals("") || inamount.equals("")) {

            } else {
                dataList.add(
                    dataList.size,
                    needData(inname.text.toString(), inamount.text.toString(), 0)
                )
                inname.setText("")
                inamount.setText("")
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

