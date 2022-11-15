package com.example.refrigerator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RVDecoration(val div : Int, val mode : Int) : RecyclerView.ItemDecoration(){
    override fun getItemOffsets(outRect : Rect, view : View, parent : RecyclerView, state : RecyclerView.State)
    {
        super.getItemOffsets(outRect, view, parent, state)
//            val position = parent.getChildAdapterPosition(view)
//            val count = state.itemCount
//            val offset = 20

        if (mode == 1){
            outRect.top = div/2 + 20
            outRect.bottom = div/2 + 20
            outRect.left = div/2
            outRect.right = div/2
        }else if (mode == 2){
            outRect.left = div
        }
        else if (mode == 3){
            outRect.right = div
            outRect.left = div
        }else if (mode == 4){
            outRect.right = div
        }
//            if(position == 0) {
//                outRect.top = offset
//            } else if (position==count-1){
//                outRect.bottom = offset
//            } else {
//                outRect.top = offset
//                outRect.bottom = offset
//            }

    }
}
