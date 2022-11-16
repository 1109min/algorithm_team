package com.example.refrigerator

import android.content.Context
import android.content.Intent
import android.graphics.Color
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.homeRv.layoutManager = GridLayoutManager(getActivity(),2)

        val adapter = ResultRVAdapter(ResultList)

        binding.homeRv.adapter = adapter
        binding.homeRv.addItemDecoration(RVDecoration(30,1))
        //터치 시 화면 송출
        ResultList.apply {
            add(ResultData("김치찌개","김치",100,"00-00",R.drawable.pic8_seafood))
            add(ResultData("순두부찌개","순두부",100,"00-00",R.drawable.pic8_seafood))
            add(ResultData("된장찌개","된장",100,"00-00",R.drawable.pic7_wine))
            add(ResultData("김치찌개","김치",100,"00-00",R.drawable.pic8_seafood))
            add(ResultData("순두부찌개","순두부",100,"00-00",R.drawable.pic8_seafood))
            add(ResultData("된장찌개","된장",100,"00-00",R.drawable.pic7_wine))
            add(ResultData("김치찌개","김치",100,"00-00",R.drawable.pic8_seafood))
            add(ResultData("순두부찌개","순두부",100,"00-00",R.drawable.pic8_seafood))
            add(ResultData("된장찌개","된장",1100,"00-00",R.drawable.pic7_wine))
        }
        adapter.notifyDataSetChanged()
        fab_open = AnimationUtils.loadAnimation(this.activity, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this.activity, R.anim.fab_close);

        //각 아이템을 클릭했을 때
        adapter.setMyItemClickListener(object : ResultRVAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {

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

            }override fun onLongClick(position: Int){

            }
        })

        binding.clickItem.setOnClickListener{

        }
        binding.closeBtn.setOnClickListener{
            binding.clickItem.startAnimation(fab_close)
            binding.clickItem.isClickable=false
            window=false
            binding.clickItem.visibility = View.GONE
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