package com.example.refrigerator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.refrigerator.databinding.ActivityAdd2Binding
import kotlin.collections.ArrayList

class Add2Activity : AppCompatActivity() {

    private val binding: ActivityAdd2Binding by lazy {
        ActivityAdd2Binding.inflate(layoutInflater)
    }
    val dataList: ArrayList<IngredientData> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var intent = Intent(this, MainActivity::class.java)

        binding.addRv.layoutManager = LinearLayoutManager(this,
            RecyclerView.VERTICAL,false)

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

        binding.menuAddBtn.setOnClickListener {



            intent.putExtra("ingredient",dataList)
            setResult(RESULT_OK, intent)
            finish()
        }
        binding.addBtn.setOnClickListener {
            if (inname.equals("") || inamount.equals("")) {

            } else {
                dataList.add(
                    dataList.size,
                    IngredientData(inname.text.toString(),inamount.text.toString(),indate.text.toString(),0,0))

                inname.setText("")
                inamount.setText("")
                indate.setText("00-00-00")

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

