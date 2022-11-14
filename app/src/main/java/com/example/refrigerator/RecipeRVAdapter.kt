package com.example.refrigerator

import android.annotation.SuppressLint
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.refrigerator.databinding.RecipeItemLayoutBinding


class RecipeRVAdapter(private val dataList: ArrayList<RecipeData>): RecyclerView.Adapter<RecyclerView.ViewHolder>(),ItemTouchHelperListener {

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
    inner class DataViewHolder(private val viewBinding: RecipeItemLayoutBinding): RecyclerView.ViewHolder(viewBinding.root){

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
        fun bind(data: RecipeData) {
//            if(data.profile_src == R.drawable.ic_profile_default){
//                viewBinding.story.borderWidth = 0
//            }
            viewBinding.ingredientName.text = data.name
            viewBinding.ingredientAmount.text = data.amount.toString() + "g"
            viewBinding.ingredientPeriod.text = data.ingredient


//            if (data.read) {
//                checkRead.put(adapterPosition, true)
//            }else {
//                checkRead.put(adapterPosition, false)
//            }
//
//
//            if (checkRead[adapterPosition] == false){
//                viewBinding.storyBorder.setImageResource(R.drawable.ic_story_border)
//                data.read = false
//            }else{
//                viewBinding.storyBorder.setImageResource(R.drawable.ic_stroy_border_read)
//                data.read = true
//            }


//            viewBinding.story.setOnClickListener {
//                if (!data.read) {
//                    checkRead.put(adapterPosition, true)
//                   // posts.contains(data.user_id)
//                }else {
//                    checkRead.put(adapterPosition, false)
//                }
//                data.read = !data.read
//                notifyItemChanged(adapterPosition)
//            }
        }
    }

    //    override fun getItemViewType(position: Int): Int {
//        return dataList[position].type
//    }
    //viewHolder 만들어질때 실행할 동작들
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val myBinding = RecipeItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(myBinding)
    }

    //viewHolder가 실제로 데이터를 표시해야할 때 호출되는 함수
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DataViewHolder).bind(dataList[position])
    }
    //표현할 item의 총 개수
    override fun getItemCount(): Int = dataList.size

    fun addIitem(data: RecipeData,position: Int){
        dataList.add(position,data)
    }
    fun removeItem(position: Int){
        dataList.removeAt(position)
    }

    override fun onItemMove(from_position: Int, to_position: Int): Boolean {
        val name = dataList[from_position]
        // 리스트 갱신
        dataList.removeAt(from_position)
        dataList.add(to_position, name)

        // fromPosition에서 toPosition으로 아이템 이동 공지
        notifyItemMoved(from_position, to_position)
        return true
    }

    override fun onItemSwipe(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)    }
}