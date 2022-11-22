package com.example.instagram_logan

import android.annotation.SuppressLint
import android.graphics.Rect
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.refrigerator.TestData
import com.example.refrigerator.TestLocalData
import com.example.refrigerator.databinding.TestMeItemBinding
import com.example.refrigerator.databinding.TestOtherItemBinding


class TestRVAdapter(private val dataList: ArrayList<TestLocalData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val checkRead = SparseBooleanArray()

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onLongClick(position: Int)
    }
    fun setItemClickListener(param: Any) {}

    private lateinit var mItemClickListener: OnItemClickListener
    fun setMyItemClickListener(itemClickListener: OnItemClickListener){
        mItemClickListener = itemClickListener
    }


    //viewHolder 객체
    inner class DataViewHolder(private val viewBinding: TestMeItemBinding): RecyclerView.ViewHolder(viewBinding.root){

        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
            itemView.setOnLongClickListener{
                mItemClickListener.onLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }
        @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
        fun bind(data: TestLocalData) {
//
//
        }
    }
    inner class MultiViewHolder1(private val viewBinding: TestOtherItemBinding): RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(data: TestLocalData) {
//
        }
    }
    override fun getItemViewType(position: Int): Int {
        return dataList[position].me
    }
    //viewHolder 만들어질때 실행할 동작들
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            1 -> {
                val myBinding = TestMeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                DataViewHolder(myBinding)
            }
            2 -> {
                val otherBinding = TestOtherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                MultiViewHolder1(otherBinding)
            }else ->{
                val otherBinding = TestOtherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                MultiViewHolder1(otherBinding)
            }
        }
    }

    //viewHolder가 실제로 데이터를 표시해야할 때 호출되는 함수
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(dataList[position].me) {
            0 -> {
                (holder as DataViewHolder).bind(dataList[position])
                //holder.setIsRecyclable(false)
            }
            1 -> {
                (holder as MultiViewHolder1).bind(dataList[position])
                //holder.setIsRecyclable(false)
            }else -> {
                (holder as MultiViewHolder1).bind(dataList[position])

            }
        }
    }
    //표현할 item의 총 개수
    override fun getItemCount(): Int = dataList.size

    fun addUserItems(data: TestLocalData){
        dataList.add(data)
        notifyItemInserted(getItemCount()-1)
    }
}