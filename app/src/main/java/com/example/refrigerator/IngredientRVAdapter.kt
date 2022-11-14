package com.example.refrigerator

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.refrigerator.databinding.IngredientItemLayoutBinding
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class IngredientRVAdapter(private val dataList: ArrayList<IngredientData>): RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperListener {

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
    inner class DataViewHolder(private val viewBinding: IngredientItemLayoutBinding): RecyclerView.ViewHolder(viewBinding.root){

//        init {
//            itemView.setOnClickListener {
//                mItemClickListener.onItemClick(adapterPosition)
//            }
//            itemView.setOnLongClickListener{
//                mItemClickListener.onLongClick(adapterPosition)
//                return@setOnLongClickListener true
//            }
//        }
        @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
        fun bind(data: IngredientData) {
//            if(data.profile_src == R.drawable.ic_profile_default){
//                viewBinding.story.borderWidth = 0
//            }
            viewBinding.ingredientName.text = data.name
            viewBinding.ingredientAmount.text = data.amount.toString() + "g"
            viewBinding.ingredientPeriod.text = data.dateString

            var current = Timestamp(System.currentTimeMillis())
            var sdf = SimpleDateFormat("yyyy-MM-dd")
            var currentdate = sdf.format(current)

            val date1 = currentdate //날짜1
            val date2 = data.dateString //날짜2

            val format1: Date = SimpleDateFormat("yyyy-MM-dd").parse(date1)
            val format2: Date = SimpleDateFormat("yyyy-MM-dd").parse(date2)

            val diffSec: Long = (format2.getTime() - format1.getTime()) / 1000 //초 차이
            val diffDays = diffSec / (24 * 60 * 60) //일자수 차이

            when {
                diffDays > 7 -> {
                    viewBinding.ingredientPeriod.setTextColor(Color.parseColor("#000000"))
                }
                diffDays in 1..7 -> {
                    viewBinding.ingredientPeriod.setTextColor(Color.parseColor("#ff7f00"))
                }
                else -> {
                    viewBinding.ingredientPeriod.setTextColor(Color.RED)
                }
            }






        }
    }

//    override fun getItemViewType(position: Int): Int {
//        return dataList[position].type
//    }
    //viewHolder 만들어질때 실행할 동작들
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val myBinding = IngredientItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return DataViewHolder(myBinding)
    }

    //viewHolder가 실제로 데이터를 표시해야할 때 호출되는 함수
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                (holder as DataViewHolder).bind(dataList[position])
    }
    //표현할 item의 총 개수
    override fun getItemCount(): Int = dataList.size

    fun addUserItems(data: IngredientData){
        dataList.add(data)
        notifyItemInserted(getItemCount()-1)
    }

    // 아이템을 드래그되면 호출되는 메소드
    override fun onItemMove(from_position: Int, to_position: Int): Boolean {
        val name = dataList[from_position]
        // 리스트 갱신
        dataList.removeAt(from_position)
        dataList.add(to_position, name)

        // fromPosition에서 toPosition으로 아이템 이동 공지
        notifyItemMoved(from_position, to_position)
        return true
    }

    // 아이템 스와이프되면 호출되는 메소드
    override fun onItemSwipe(position: Int) {
        // 리스트 아이템 삭제
       // dataList.removeAt(position)
        // 아이템 삭제되었다고 공지
        notifyItemRemoved(position)
    }
}