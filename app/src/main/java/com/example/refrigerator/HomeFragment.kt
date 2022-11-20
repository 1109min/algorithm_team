package com.example.refrigerator

import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.refrigerator.databinding.ActivityMainBinding
import com.example.refrigerator.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Comparator


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    val ResultList: ArrayList<ResultData> = arrayListOf()
    var helper: ItemTouchHelper? = null

    lateinit var intent: Intent
    private var fab_open: Animation? = null
    private var fab_close: Animation? = null
    val adapter = ResultRVAdapter(ResultList)

    private lateinit var callback: OnBackPressedCallback
    var window: Boolean = false

    var firestore: FirebaseFirestore? = null
    val handler: Handler = object : Handler(Looper.getMainLooper()) {
        @RequiresApi(Build.VERSION_CODES.N)
        override fun handleMessage(msg: Message) {
            if (msg.what == 1) {
//                //터치 시 화면 송출
//                ResultList.clear()
//                ResultList.apply {
//                    add(
//                        ResultData(
//                            "김치찌개", arrayListOf(
//                                needData("김치", "100g", 0),
//                                needData("돼지고기", "100g", 0),
//                                needData("대파", "30g", 0),
//                                needData("마늘", "20g", 0),
//                            ), "2022-10-31", R.drawable.pic8_seafood, 0
//                        )
//                    )
//
//                    add(
//                        ResultData(
//                            "된장찌개", arrayListOf(
//                                needData("된장", "300g", 0),
//                                needData("양파", "300g", 0),
//                                needData("소금", "5g", 0),
//                                needData("마늘", "20g", 0),
//                                needData("청양고추", "40g", 0),
//                                needData("설탕", "5g", 0),
//                                needData("애호박", "100g", 0),
//                                needData("감자", "50g", 0),
//                            ), "2022-11-02", R.drawable.pic1_barbecue, 0
//                        )
//                    )
//
//                    add(
//                        ResultData(
//                            "부대찌개", arrayListOf(
//                                needData("돼지고기", "200g", 0),
//                                needData("양파", "50g", 0),
//                                needData("후추", "50g", 0),
//                                needData("대파", "20g", 0),
//                                needData("김치", "50g", 0),
//                                needData("마늘", "100g", 0),
//                                needData("소시지", "50g", 0),
//                            ), "2022-11-07", R.drawable.pic2_dairy_products, 0
//                        )
//                    )
//
//                    add(
//                        ResultData(
//                            "돼지고기숙주볶음", arrayListOf(
//                                needData("돼지고기", "180g", 0),
//                                needData("숙주", "210g", 0),
//                                needData("대파", "50g", 0),
//                                needData("마늘", "50g", 0)
//                            ), "2022-11-10", R.drawable.pic3_fruit, 0
//                        )
//                    )
//
//                    add(
//                        ResultData(
//                            "오징어볶음", arrayListOf(
//                                needData("오징어", "600g", 0),
//                                needData("양배추", "100g", 0),
//                                needData("당근", "200g", 0),
//                                needData("양파", "150g", 0),
//                                needData("파", "100g", 0),
//                                needData("마늘", "20g", 0),
//                                needData("청양고추", "20g", 0),
//                                needData("설탕", "10g", 0)
//                            ), "2022-11-19", R.drawable.pic2_dairy_products, 0
//                        )
//                    )
//                }
//                ResultList.reverse()

                //정렬
                var byYear = Comparator.comparing { obj: ResultData -> obj.date.split("-")[0] }
                var byMonth = Comparator.comparing { obj: ResultData -> obj.date.split("-")[1] }
                var byday = Comparator.comparing { obj: ResultData -> obj.date.split("-")[2] }
                ResultList.sortWith(byYear.thenComparing(byMonth.thenComparing(byday)))
                ResultList.reverse()

                for (i in 0 until ResultList.size) {
                    firestore!!.collection("results").document(i.toString())
                        .set(ResultList[i])
                    Log.d(
                        "wow",
                        ResultList.size.toString() + " " + ResultList[i].toString() + "kjkjk " + i
                    )
                }
                firestore!!.collection("currents").document("0")
                    .delete()
            }
        }
    }

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
        binding.homeRv.layoutManager = GridLayoutManager(getActivity(), 2)



        binding.homeRv.adapter = adapter
        binding.homeRv.addItemDecoration(RVDecoration(50, 1))

        val anim = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.anim_slide)
        binding.homeRv.layoutAnimation = anim
        binding.homeRv.scheduleLayoutAnimation()

        binding.clickItem.visibility = GONE
        firestore = FirebaseFirestore.getInstance()
        var results: ArrayList<ResultData> = arrayListOf()

        //읽어오기
        firestore?.collection("results")
            ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // ArrayList 비워줌
                ResultList.clear()

                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(ResultData::class.java)
                    ResultList.add(item!!)
                    Log.d("wow", "gg")
                }
                adapter.notifyDataSetChanged()
            }

        var currentData: ArrayList<ResultData> = arrayListOf()

        firestore = FirebaseFirestore.getInstance()
        firestore?.collection("currents")
            ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // ArrayList 비워줌

                var i: Int = 0
                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(ResultData::class.java)
                    currentData.add(item!!)
                    Log.d("wow", "fff")
                    ResultList.add(ResultList.size,currentData[0])
                    i++
                }
                handler.sendEmptyMessage(1)
            }


        //터치 시 화면 송출
        ResultList.apply {
            add(
                ResultData(
                    "김치찌개", arrayListOf(
                        needData("김치", "100g", 0),
                        needData("돼지고기", "100g", 0),
                        needData("대파", "30g", 0),
                        needData("마늘", "20g", 0),
                    ), "2022-10-31", R.drawable.pic8_seafood, 0
                )
            )

            add(
                ResultData(
                    "된장찌개", arrayListOf(
                        needData("된장", "300g", 0),
                        needData("양파", "300g", 0),
                        needData("소금", "5g", 0),
                        needData("마늘", "20g", 0),
                        needData("청양고추", "40g", 0),
                        needData("설탕", "5g", 0),
                        needData("애호박", "100g", 0),
                        needData("감자", "50g", 0),
                    ), "2022-11-02", R.drawable.pic1_barbecue, 0
                )
            )

            add(
                ResultData(
                    "부대찌개", arrayListOf(
                        needData("돼지고기", "200g", 0),
                        needData("양파", "50g", 0),
                        needData("후추", "50g", 0),
                        needData("대파", "20g", 0),
                        needData("김치", "50g", 0),
                        needData("마늘", "100g", 0),
                        needData("소시지", "50g", 0),
                    ), "2022-11-07", R.drawable.pic2_dairy_products, 0
                )
            )

            add(
                ResultData(
                    "돼지고기숙주볶음", arrayListOf(
                        needData("돼지고기", "180g", 0),
                        needData("숙주", "210g", 0),
                        needData("대파", "50g", 0),
                        needData("마늘", "50g", 0)
                    ), "2022-11-10", R.drawable.pic3_fruit, 0
                )
            )

            add(
                ResultData(
                    "오징어볶음", arrayListOf(
                        needData("오징어", "600g", 0),
                        needData("양배추", "100g", 0),
                        needData("당근", "200g", 0),
                        needData("양파", "150g", 0),
                        needData("파", "100g", 0),
                        needData("마늘", "20g", 0),
                        needData("청양고추", "20g", 0),
                        needData("설탕", "10g", 0)
                    ), "2022-11-19", R.drawable.pic2_dairy_products, 0
                )
            )
        }

        var byYear = Comparator.comparing { obj: ResultData -> obj.date.split("-")[0] }
        var byMonth = Comparator.comparing { obj: ResultData -> obj.date.split("-")[1] }
        var byday = Comparator.comparing { obj: ResultData -> obj.date.split("-")[2] }
        ResultList.sortWith(byYear.thenComparing(byMonth.thenComparing(byday)))
        ResultList.reverse()

        //쓰기

//        for(i in 0 until ResultList.size) {
//            firestore!!.collection("results").document(i.toString())
//                .set(ResultList[i])
//            Log.d("wow",ResultList.size.toString()+" "+ResultList[i].toString()+"kjkjk "+i)
//        }

        adapter.notifyDataSetChanged()
        fab_open = AnimationUtils.loadAnimation(this.activity, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this.activity, R.anim.fab_close);


        var star1 = binding.star1
        var star2 = binding.star2
        var star3 = binding.star3
        var star4 = binding.star4
        var star5 = binding.star5


        var po: Int = 0
        //각 아이템을 클릭했을 때
        adapter.setMyItemClickListener(object : ResultRVAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                po = position
                if (window == false) {
                    binding.clickItem.startAnimation(fab_open)
                    window = true
                    binding.clickItem.visibility = View.VISIBLE
                    binding.clickItem.isClickable = true
                }

                binding.nameItem.text = ResultList[position].name
                binding.dateItem.text = ResultList[position].date

                var info: String = ""
                for (i in 0 until ResultList[position].ingredients.size - 1) {

                    var new_info =
                        ResultList[position].ingredients[i].name + " : " + ResultList[position].ingredients[i].amount


                    if (i % 2 == 0) {
                        new_info += "\n"
                    } else {
                        new_info += "   "
                    }
                    info += new_info
                }
                var new_info =
                    ResultList[position].ingredients[ResultList[position].ingredients.size - 1].name + " : " + ResultList[position].ingredients[ResultList[position].ingredients.size - 1].amount

                info += new_info
                binding.ingredsItem.text = info
                binding.picItem.setImageResource(ResultList[position].pic)
                when (ResultList[po].star) {
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

            }

            override fun onLongClick(position: Int) {
                ResultList.removeAt(position)
                firestore!!.collection("results").document(position.toString())
                    .delete()
                for (i in 0 until ResultList.size) {
                    firestore!!.collection("results").document(i.toString())
                        .set(ResultList[i])
                }
                firestore!!.collection("results").document(ResultList.size.toString())
                    .delete()
                adapter.notifyDataSetChanged()

            }
        })


        //닫힘 눌렀을 때
        binding.clickItem.setOnClickListener {

        }
        binding.closeBtn.setOnClickListener {
            binding.clickItem.startAnimation(fab_close)
            binding.clickItem.isClickable = false
            window = false
            binding.clickItem.visibility = View.GONE
        }
//별점


        star1.setOnClickListener {

            if (ResultList[po].star != 1) {
                star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
                star2.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star3.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star4.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)

                ResultList[po].star = 1
            } else {
                star1.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star2.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star3.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star4.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)

                ResultList[po].star = 0
            }
            adapter.notifyDataSetChanged()
        }
        star2.setOnClickListener {
            star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star2.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star3.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
            star4.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
            star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
            ResultList[po].star = 2
            adapter.notifyDataSetChanged()
        }
        star3.setOnClickListener {
            star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star2.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star3.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star4.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
            star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
            ResultList[po].star = 3
            adapter.notifyDataSetChanged()
        }
        star4.setOnClickListener {
            star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star2.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star3.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star4.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
            ResultList[po].star = 4
            adapter.notifyDataSetChanged()
        }
        star5.setOnClickListener {
            star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star2.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star3.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star4.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star5.setBackgroundResource(R.drawable.ic_baseline_star_24)
            ResultList[po].star = 5
            adapter.notifyDataSetChanged()
        }

        val intentMake = Intent(this.context, MakeActivity::class.java)

        binding.makeBtn.setOnClickListener {
            startActivity(intentMake)
        }


        //별점
        star1.setOnClickListener {

            if (ResultList[po].star != 1) {
                star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
                star2.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star3.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star4.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)

                ResultList[po].star = 1
            } else {
                star1.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star2.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star3.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star4.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
                star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)

                ResultList[po].star = 0
            }
            adapter.notifyDataSetChanged()
        }
        star2.setOnClickListener {
            star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star2.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star3.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
            star4.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
            star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
            ResultList[po].star = 2
            adapter.notifyDataSetChanged()
        }
        star3.setOnClickListener {
            star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star2.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star3.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star4.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
            star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
            ResultList[po].star = 3
            adapter.notifyDataSetChanged()
        }
        star4.setOnClickListener {
            star1.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star2.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star3.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star4.setBackgroundResource(R.drawable.ic_baseline_star_24)
            star5.setBackgroundResource(R.drawable.ic_baseline_star_outline_24)
            ResultList[po].star = 4
            adapter.notifyDataSetChanged()
        }
        star5.setOnClickListener {
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
                if (window == true) {
                    binding.clickItem.startAnimation(fab_close)
                    window = false
                    binding.clickItem.visibility = View.GONE
                    binding.clickItem.isClickable = false
                } else {
                    (activity as MainActivity?)
                        ?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(
                            ActivityMainBinding.inflate(layoutInflater).containerFragment.id,
                            HomeFragment()
                        )
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

    override fun onResume() {

        adapter.notifyDataSetChanged()
        super.onResume()
    }
}