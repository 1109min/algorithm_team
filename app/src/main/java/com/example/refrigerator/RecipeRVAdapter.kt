package com.example.refrigerator

import android.annotation.SuppressLint
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.refrigerator.databinding.RecipeItemLayoutBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList


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
    inner class DataViewHolder( val viewBinding: RecipeItemLayoutBinding): RecyclerView.ViewHolder(viewBinding.root){

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
            viewBinding.ingredientAmount.text = data.ingredient[0].amount
            viewBinding.ingredientPeriod.text = data.ingredient[0].name



            var info:String = ""
            if (data.state == 1){
                viewBinding.needed.visibility = VISIBLE
                for(i in 0 until data.ingredient.size-1) {

                    var new_info =
                        data.ingredient[i].name + " : " + data.ingredient[i].amount


                    if(i%2==0){
                        new_info += "\n"
                    }else{
                        new_info += "   "
                    }
                    info += new_info
                }
                var new_info =
                    data.ingredient[data.ingredient.size-1].name + " : " + data.ingredient[data.ingredient.size-1].amount

                info += new_info
                viewBinding.needed.text = info
                data.click = 2
            }else if(data.state == 0 || data.click == 2){
                viewBinding.needed.visibility = GONE
                    data.click=0
            }

            var pic:Int
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


            viewBinding.ingredientPic.setImageResource(pic)


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
        if(dataList[position].click==1 || dataList[position].click==1) {
                (holder as DataViewHolder).bind(dataList[position])
        }
        else{
                (holder as DataViewHolder).viewBinding.root.animation =
                AnimationUtils.loadAnimation(holder.viewBinding.root.context, R.anim.recipe_anim)
                (holder as DataViewHolder).bind(dataList[position])
        }
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