package com.example.refrigerator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.refrigerator.databinding.ActivityAdd1Binding
import kotlin.collections.ArrayList

class Add1Activity : AppCompatActivity() {

    private val binding: ActivityAdd1Binding by lazy {
        ActivityAdd1Binding.inflate(layoutInflater)
    }
    val dataList: ArrayList<needData> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.addRv.layoutManager = LinearLayoutManager(this,
            RecyclerView.VERTICAL,false)

        val adapter = AddMenuRVAdapter(dataList)

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

        binding.menuAddBtn.setOnClickListener {

            menudata.add(0,RecipeData(binding.menuNameET.text.toString(),dataList,0,0,0))

            intent.putExtra("menu",menudata)
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
        startActivity(intent)
        super.onBackPressed()
    }
}

