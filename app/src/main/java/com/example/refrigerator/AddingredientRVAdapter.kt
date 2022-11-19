package com.example.refrigerator

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.refrigerator.databinding.AddEdt2LayoutBinding
import com.example.refrigerator.databinding.AddEdtLayoutBinding
import com.example.refrigerator.databinding.AddLayoutBinding
import com.example.refrigerator.databinding.IngredientItemLayout2Binding
import com.example.refrigerator.databinding.IngredientItemLayoutBinding
import com.example.refrigerator.databinding.ResultItemLayoutBinding
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddingredientRVAdapter(private val dataList: ArrayList<IngredientData>): RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperListener {

    private val checkRead = SparseBooleanArray()
    var canRemove : Boolean = true

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
    inner class DataViewHolder(private val viewBinding: AddEdt2LayoutBinding): RecyclerView.ViewHolder(viewBinding.root){

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
        fun bind(data: IngredientData) {

            viewBinding.nameItem.setText(data.name)

            viewBinding.amountItem.setText(data.amount)

            viewBinding.dateItem.setText(data.dateString)

            viewBinding.picItem.setImageResource(R.drawable.pic1_barbecue)
        }
    }

//    override fun getItemViewType(position: Int): Int {
//        return dataList[position].type
//    }

    //viewHolder 만들어질때 실행할 동작들
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val Binding = AddEdt2LayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)

        return   DataViewHolder(Binding)

    }

    //viewHolder가 실제로 데이터를 표시해야할 때 호출되는 함수
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as DataViewHolder).bind(dataList[position])
        //holder.setIsRecyclable(false)


    }
    //표현할 item의 총 개수
    override fun getItemCount(): Int = dataList.size

    fun addItem(data : IngredientData) {
        dataList.add(data);
    }

    fun getItem(position : Int): IngredientData {
        return dataList.get(position);
    }



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
        val name = dataList[position]
        if(canRemove) {
            dataList.removeAt(position)
            // 아이템 삭제되었다고 공지
            notifyItemRemoved(position)
        }else {
            addUserItems(name)
            notifyItemChanged(position)
        }
    }
}