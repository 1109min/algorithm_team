package com.example.refrigerator//package com.example.refrigerator

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.TELEPHONY_SERVICE
import android.content.Intent
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram_logan.TestRVAdapter
import com.example.refrigerator.databinding.FragmentSettingBinding
import com.example.refrigerator.databinding.FragmentTab2Binding
import com.example.refrigerator.databinding.RecipeItemLayoutBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class SettingFragment: Fragment() {

    lateinit var binding: FragmentSettingBinding
    lateinit var mainActivity: MainActivity

    var testLocalDataList : ArrayList<TestLocalData> = arrayListOf()
    val testDataList: ArrayList<TestData> = arrayListOf()



    override fun onAttach(context: Context) {
        super.onAttach(context)

        // 2. Context를 Activity로 형변환하여 할당
        mainActivity = context as MainActivity
    }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)




        var firestore: FirebaseFirestore? = null
        var uid: String? = null
        uid = FirebaseAuth.getInstance().currentUser?.uid
        firestore = FirebaseFirestore.getInstance()

        val adapter = TestRVAdapter(testLocalDataList)



        binding.chatBtn.setOnClickListener {
            for (i in 0 until testDataList.size) {
                firestore!!.collection("tests").document(i.toString())
                    .set(testDataList[i])
            }
        }


        firestore?.collection("tests")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            // ArrayList 비워줌
            testDataList.clear()

            for (snapshot in querySnapshot!!.documents) {
                var item = snapshot.toObject(TestData::class.java)
                testDataList.add(item!!)

                var byYear = Comparator.comparing { obj: TestData -> obj.time.split("-")[0] }
                var byMonth = Comparator.comparing { obj: TestData -> obj.time.split("-")[1] }
                var byday = Comparator.comparing { obj: TestData -> obj.time.split("-")[2] }
                var byhour = Comparator.comparing { obj: TestData -> obj.time.split("-")[3] }
                var byminute = Comparator.comparing { obj: TestData -> obj.time.split("-")[4] }
                var bysecond = Comparator.comparing { obj: TestData -> obj.time.split("-")[5] }

                testDataList.sortWith(
                    byYear.thenComparing(
                        byMonth.thenComparing(
                            byday.thenComparing(
                                byhour.thenComparing(byminute.thenComparing(bysecond))
                            )
                        )
                    )
                )
                for(i in 0 until testDataList.size){
//                    if(testDataList[i].user_num == 1)
                }
                adapter.notifyDataSetChanged()
            }
        }

        binding.settRV.layoutManager = LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false)


        //binding.mainFeed.adapter = Postadapter
        binding.settRV.adapter = adapter

        binding.settRV.addItemDecoration(RVDecoration(40,1))


        //각 아이템을 클릭했을 때
        adapter.setMyItemClickListener(object : TestRVAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {

                adapter.notifyItemChanged(position)
            }override fun onLongClick(position: Int) {

                adapter.notifyDataSetChanged()

            }
        })


        adapter.notifyDataSetChanged()




        return binding.root
    }
}
