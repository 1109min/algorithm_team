package com.example.refrigerator

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.refrigerator.databinding.ActivityMainBinding
import com.example.refrigerator.databinding.FragmentTab1Binding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Math.random
import java.util.*


class Tab1Fragment: Fragment() {

    lateinit var binding: FragmentTab1Binding
    val ingredientList: ArrayList<IngredientData> = arrayListOf()
    var helper: ItemTouchHelper? = null
    var ingredientName: ArrayList<String> = ArrayList()
    var ingredientAmount: ArrayList<String> = ArrayList()
    var ingredientTime: ArrayList<String> = ArrayList()
    var arrayList: ArrayList<Any> = ArrayList()
    lateinit var intent : Intent
    private var fab_open: Animation? = null
    private  var fab_close: Animation? = null

    private lateinit var callback: OnBackPressedCallback
    var window: Boolean = false

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentTab1Binding.inflate(inflater, container, false)

        //binding.tab1RV.layoutManager = LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false)
        binding.tab1RV.layoutManager = GridLayoutManager(getActivity(),4)

        val adapter = IngredientRVAdapter(ingredientList)


        binding.tab1RV.adapter = adapter


        val anim = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.anim_slide)
        binding.tab1RV.layoutAnimation = anim
        binding.tab1RV.scheduleLayoutAnimation()

        binding.tab1RV.addItemDecoration(RVDecoration(30,1))
        //터치 시 화면 송출

        fab_open = AnimationUtils.loadAnimation(this.activity, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this.activity, R.anim.fab_close);

        //각 아이템을 클릭했을 때
        adapter.setMyItemClickListener(object : IngredientRVAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {

                if(window==false){
                    binding.clickItem.startAnimation(fab_open)
                    window = true
                    binding.clickItem.visibility = VISIBLE
                    binding.clickItem.isClickable=true
                }

                binding.nameItem.text = ingredientList[position].name
                binding.amountItem.text = ingredientList[position].amount+"g"
                binding.dateItem.text = ingredientList[position].dateString
                binding.picItem.setImageResource(ingredientList[position].pic)

                    when(ingredientList[position].late) { //ff7f00 00de00
                        2 -> binding.dateItem.setBackgroundResource(R.drawable.date_after)
                        1 -> binding.dateItem.setBackgroundResource(R.drawable.date_caution)
                        0 -> binding.dateItem.setBackgroundResource(R.drawable.date_safe)
                    }

            }override fun onLongClick(position: Int){

            }
        })

        binding.clickItem.setOnClickListener{

        }
        binding.closeBtn.setOnClickListener{
            binding.clickItem.startAnimation(fab_close)
            binding.clickItem.isClickable=false
            window=false
            binding.clickItem.visibility = GONE
        }

        //firebase 연동
        var firestore: FirebaseFirestore? = null
        var uid: String? = null
        uid = FirebaseAuth.getInstance().currentUser?.uid
        val format = SimpleDateFormat("yyyy-MM-dd")


        firestore = FirebaseFirestore.getInstance();
        firestore.collection("sourcefile").document("ingredients")
            .addSnapshotListener { value, error ->
                Log.d("event", value.toString())
                arrayList.clear()
                arrayList.addAll((value?.data?.get("ingredient") as ArrayList<*>))

                //배열에 저장
                for (i in 0 until arrayList.size) {
                    try {
                        if (i % 3 == 0){
                            ingredientName.add(arrayList[i] as String)
                        }
                        else if (i % 3 == 1) {
                            ingredientTime.add(format.format(toTimeStamp(arrayList[i].toString())))
                        }
                        else if (i % 3 == 2){
                            ingredientAmount.add(arrayList[i] as String)
                        }

                    } catch (e: Exception) {
                        Log.d("error:$i", e.toString())
                    }

                }

                for (i in 0 until ingredientName.size) {

                    //사진 예시 용
                    var pic:Int = 0
                    when(Random().nextInt(9)){
                        0 -> pic = R.drawable.pic1_barbecue
                        1 -> pic = R.drawable.pic2_dairy_products
                        2 -> pic =(R.drawable.pic3_fruit)
                        3 -> pic =(R.drawable.pic4_harvest)
                        4 -> pic =(R.drawable.pic5_kimchi)
                        5 -> pic =(R.drawable.pic6_vegetable)
                        6 -> pic =(R.drawable.pic7_wine)
                        8 -> pic =(R.drawable.pic8_seafood)
                        else -> pic =(R.drawable.pic1_barbecue)
                    }

                    ingredientList.add(
                        IngredientData(
                            ingredientName[i],
                            ingredientAmount[i],
                            ingredientTime[i],
                            pic,
                            0
                        )
                    )
                }
                //남은 날짜 이른 순으로 정렬
                var byYear = Comparator.comparing { obj: IngredientData -> obj.dateString.split("-")[0]}
                var byMonth = Comparator.comparing { obj: IngredientData -> obj.dateString.split("-")[1]}
                var byday = Comparator.comparing { obj: IngredientData -> obj.dateString.split("-")[2]}
                ingredientList.sortWith(byYear.thenComparing(byMonth.thenComparing(byday)))

                adapter.notifyDataSetChanged()
            }


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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(window == true){
                    binding.clickItem.startAnimation(fab_close)
                    window=false
                    binding.clickItem.visibility = GONE
                    binding.clickItem.isClickable=false
                }else{
                    (activity as MainActivity?)?.gohome()

                    (activity as MainActivity?)
                        ?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(ActivityMainBinding.inflate(layoutInflater).containerFragment.id,HomeFragment())
                        ?.commitAllowingStateLoss()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}
