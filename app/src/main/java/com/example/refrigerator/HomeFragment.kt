package com.example.refrigerator

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.refrigerator.databinding.ActivityMainBinding
import com.example.refrigerator.databinding.FragmentHomeBinding
import com.example.refrigerator.databinding.FragmentTab1Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Comparator


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    val ResultList: ArrayList<ResultData> = arrayListOf()
    var helper: ItemTouchHelper? = null

    lateinit var intent : Intent
    private var fab_open: Animation? = null
    private  var fab_close: Animation? = null

    private lateinit var callback: OnBackPressedCallback
    var window: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        binding.homeRv.layoutManager = GridLayoutManager(getActivity(),2)

        val adapter = ResultRVAdapter(ResultList)

        binding.homeRv.adapter = adapter
        binding.homeRv.addItemDecoration(RVDecoration(50,1))
        //터치 시 화면 송출
        ResultList.apply {
            add(ResultData("김치찌개","김치",100,"2022-10-31",R.drawable.pic8_seafood,0))
            add(ResultData("순두부찌개","순두부",100,"2022-11-02",R.drawable.pic1_barbecue,0))
            add(ResultData("된장찌개","된장",100,"2022-11-04",R.drawable.pic7_wine,0))
            add(ResultData("김치찌개","김치",100,"2022-11-07",R.drawable.pic3_fruit,0))
            add(ResultData("순두부찌개","순두부",100,"2022-11-08",R.drawable.pic4_harvest,0))
            add(ResultData("된장찌개","된장",100,"2022-11-11",R.drawable.pic5_kimchi,0))
            add(ResultData("김치찌개","김치",100,"2022-11-13",R.drawable.pic6_vegetable,0))
            add(ResultData("순두부찌개","순두부",100,"2022-11-14",R.drawable.pic8_seafood,0))
            add(ResultData("된장찌개","된장",1100,"2022-11-15",R.drawable.pic2_dairy_products,0))
        }
        var byYear = Comparator.comparing { obj: ResultData -> obj.date.split("-")[0]}
        var byMonth = Comparator.comparing { obj: ResultData -> obj.date.split("-")[1]}
        var byday = Comparator.comparing { obj: ResultData -> obj.date.split("-")[2]}
        ResultList.sortWith(byYear.thenComparing(byMonth.thenComparing(byday)))
        ResultList.reverse()

        adapter.notifyDataSetChanged()
        fab_open = AnimationUtils.loadAnimation(this.activity, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this.activity, R.anim.fab_close);


            var star1 = binding.star1
            var star2 = binding.star2
            var star3 = binding.star3
            var star4 = binding.star4
            var star5 = binding.star5


        var po : Int = 0
        //각 아이템을 클릭했을 때
        adapter.setMyItemClickListener(object : ResultRVAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                po = position
                if(window==false){
                    binding.clickItem.startAnimation(fab_open)
                    window = true
                    binding.clickItem.visibility = View.VISIBLE
                    binding.clickItem.isClickable=true
                }

                binding.nameItem.text = ResultList[position].name
                binding.dateItem.text = ResultList[position].date
                binding.ingredsItem.text = ResultList[position].ingredient
                binding.picItem.setImageResource(ResultList[position].pic)
                when(ResultList[po].star){
                    0 -> {
                        star1.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                        star2.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                        star3.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                        star4.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                        star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                    }
                    1 -> {
                        star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
                        star2.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                        star3.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                        star4.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                        star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                    }
                    2 -> {
                        star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
                        star2.setBackgroundResource(R.drawable.ic_baseline_star_24)
                        star3.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                        star4.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                        star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                    }
                    3 -> {
                        star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
                        star2.setBackgroundResource(R.drawable.ic_baseline_star_24)
                        star3.setBackgroundResource(R.drawable.ic_baseline_star_24)
                        star4.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                        star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                    }
                    4 -> {
                        star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
                        star2.setBackgroundResource(R.drawable.ic_baseline_star_24)
                        star3.setBackgroundResource(R.drawable.ic_baseline_star_24)
                        star4.setBackgroundResource(R.drawable.ic_baseline_star_24)
                        star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                    }
                    5 -> {
                        star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
                        star2.setBackgroundResource(R.drawable.ic_baseline_star_24)
                        star3.setBackgroundResource(R.drawable.ic_baseline_star_24)
                        star4.setBackgroundResource(R.drawable.ic_baseline_star_24)
                        star5.setBackgroundResource(R.drawable.ic_baseline_star_24)
                    }
                }

            }override fun onLongClick(position: Int){

            }
        })


        //닫힘 눌렀을 때
        binding.clickItem.setOnClickListener{

        }
        binding.closeBtn.setOnClickListener{
            binding.clickItem.startAnimation(fab_close)
            binding.clickItem.isClickable=false
            window=false
            binding.clickItem.visibility = View.GONE
        }

        val intentMake = Intent(this.context,MakeActivity::class.java)

        binding.makeBtn.setOnClickListener{
            startActivity(intentMake)
        }




        //별점
        star1.setOnClickListener{

            if( ResultList[po].star != 1) {
                star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
                star2.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star3.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star4.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)

                ResultList[po].star = 1
            }else{
                star1.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star2.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star3.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star4.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)

                ResultList[po].star = 0
            }
            adapter.notifyDataSetChanged()
        }
        star2.setOnClickListener{
            star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star2.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star3.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
            star4.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
            star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
            ResultList[po].star = 2
            adapter.notifyDataSetChanged()
        }
        star3.setOnClickListener{
            star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star2.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star3.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star4.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
            star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
            ResultList[po].star = 3
            adapter.notifyDataSetChanged()
        }
        star4.setOnClickListener{
            star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star2.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star3.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star4.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
            ResultList[po].star = 4
            adapter.notifyDataSetChanged()
        }
        star5.setOnClickListener{
            star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star2.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star3.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star4.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star5.setBackgroundResource(R.drawable.ic_baseline_star_24)
            ResultList[po].star = 5
            adapter.notifyDataSetChanged()
        }




        return binding.root
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(window == true){
                    binding.clickItem.startAnimation(fab_close)
                    window=false
                    binding.clickItem.visibility = View.GONE
                    binding.clickItem.isClickable=false
                }else{
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