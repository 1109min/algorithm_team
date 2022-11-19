package com.example.refrigerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.refrigerator.databinding.FragmentListBinding
import com.google.android.material.tabs.TabLayoutMediator


class ListFragment : Fragment() {
    lateinit var viewBinding: FragmentListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun setInt(){

        val listVPAdapter = ListVPAdapter(this)
        viewBinding.vpList.adapter = listVPAdapter
        //퓨페이저 연동 완료

        val tabTitleArray = arrayOf(
            "재료",
            "메뉴",
        )

        TabLayoutMediator(viewBinding.tabList, viewBinding.vpList) { tab,position ->
            tab.text = tabTitleArray[position]
        }.attach()



    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        viewBinding = FragmentListBinding.inflate(inflater, container, false)

        setInt()
        // Inflate the layout for this fragment

        return viewBinding.root //히트다 히트
    }
}