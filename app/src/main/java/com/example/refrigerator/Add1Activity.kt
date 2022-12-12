package com.example.refrigerator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

    private var fab_open: Animation? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.addRv.layoutManager = LinearLayoutManager(this,
            RecyclerView.VERTICAL,false)

        val anim = AnimationUtils.loadLayoutAnimation(this, R.anim.anim_slide)
        binding.addRv.layoutAnimation = anim
        binding.addRv.scheduleLayoutAnimation()

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

        //데이터베이스 연결
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

        ArrayAdapter.createFromResource(
            this,
            R.array.img_array,
            R.layout.spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinner.adapter = adapter

        }

        //binding.spinner.adapter = myAdapter

        var menu_pic = binding.img
        var getPic = R.drawable.chop

        binding.spinner.setSelection(13)

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when (position) {
                    0 -> { //고기요리
                        menu_pic.setImageResource(R.drawable.chop)
                        getPic = R.drawable.chop
                    }
                    1 -> { //해물요리
                        menu_pic.setImageResource(R.drawable.lobster)
                        getPic = R.drawable.lobster

                    }
                    2 -> { //밥요리
                        menu_pic.setImageResource(R.drawable.fried_rice)
                        getPic = R.drawable.fried_rice

                    }
                    3 -> { //닭요리
                        menu_pic.setImageResource(R.drawable.chicken_leg)
                        getPic = R.drawable.chicken_leg

                    }
                    4 -> { //튀김요리
                        menu_pic.setImageResource(R.drawable.frying_pan)
                        getPic = R.drawable.frying_pan

                    }
                    5 -> { //한식
                        menu_pic.setImageResource(R.drawable.bibimbap)
                        getPic = R.drawable.bibimbap

                    }
                    6 -> { //중식
                        menu_pic.setImageResource(R.drawable.dimsum)
                        getPic = R.drawable.dimsum

                    }
                    7 -> { //일식
                        menu_pic.setImageResource(R.drawable.ebi)
                        getPic = R.drawable.ebi

                    }
                    8 -> { //양식
                        menu_pic.setImageResource(R.drawable.spaghetti)
                        getPic = R.drawable.spaghetti

                    }
                    9 -> { //찌개
                        menu_pic.setImageResource(R.drawable.soup)
                        getPic = R.drawable.soup

                    }
                    10 -> { //카레
                        menu_pic.setImageResource(R.drawable.curry)
                        getPic = R.drawable.curry

                    }
                    11 -> { //샐러드
                        menu_pic.setImageResource(R.drawable.salad)
                        getPic = R.drawable.salad

                    }
                    12 -> { //기타
                        menu_pic.setImageResource(R.drawable.pic5_kimchi)
                        getPic = R.drawable.pic5_kimchi
                    }
                    13 -> { //기타
                        menu_pic.setImageResource(R.drawable.diet)
                        getPic = R.drawable.diet

                    }
                    else -> {
                        menu_pic.setImageResource(R.drawable.diet)
                        getPic = R.drawable.diet

                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            fun onNothingSelecte(parent: AdapterView<*>) {

            }
        }


        binding.menuAddBtn.setOnClickListener {

            menudata.add(0,RecipeData(binding.menuNameET.text.toString(),dataList,getPic,0,0))
            origin_recipes.add(menudata[0])

            origin_recipes.sortBy { it.name }
            for(i in 0 until origin_recipes.size) {
                firestore!!.collection("recipes").document(i.toString())
                    .set(origin_recipes[i])
                Log.d("why",i.toString())
            }


            setResult(RESULT_OK, intent)
            finish()
        }
        binding.addBtn.setOnClickListener {
            if (inname.equals("") || inamount.equals("")) {

            } else {
                var real_amount = inamount.text.toString()//split("g")[0]

                dataList.add(
                    dataList.size,
                    needData(inname.text.toString(), real_amount, 0)
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

