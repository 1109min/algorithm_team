package com.example.refrigerator

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.refrigerator.databinding.FragmentTab2Binding


class Tab2Fragment: Fragment() {

    lateinit var binding: FragmentTab2Binding
    val recipeList: ArrayList<RecipeData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentTab2Binding.inflate(inflater, container, false)

        binding.tab1RV.layoutManager = LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false)

        val adapter = RecipeRVAdapter(recipeList)

//        Storyadapter.setMyItemClickListener(object : StoryRVAdapter.OnItemClickListener {
//            override fun onItemClick(position: Int) {
//
//                if (storyList[position].read==false) {
//                    storyList[position].read=true
//
//                    Storyadapter.notifyDataSetChanged()
//                }
//                val intent = Intent(getActivity(), StoryViewActivity::class.java)
//                intent.putExtra("id",storyList[position].user_id)
//                intent.putExtra("profile",storyList[position].profile_src)
//                intent.putExtra("pic",storyList[position].story_pic)
//                intent.putExtra("read",storyList[position].read)
//                intent.putExtra("type",storyList[position].type)
//                intent.putExtra("time",storyList[position].time)
//
//                startActivity(intent)
//            }
//            override fun onLongClick(position: Int){
//
//            }
//        })

        //binding.mainFeed.adapter = Postadapter
        binding.tab1RV.adapter = adapter
        binding.tab1RV.addItemDecoration(RVDecoration(50,1))


        recipeList.apply{
            add(RecipeData("김치찌개","김치",100))
            add(RecipeData("순두부찌개","순두부",200))
            add(RecipeData("된장찌개","된장",150))
            add(RecipeData("김치찌개","김치",100))
            add(RecipeData("순두부찌개","순두부",200))
            add(RecipeData("된장찌개","된장",150))
            add(RecipeData("김치찌개","김치",100))
            add(RecipeData("순두부찌개","순두부",200))
            add(RecipeData("된장찌개","된장",150))
        }
        adapter.notifyDataSetChanged()

//            Postadapter.addUserItems(PostData(title_memo, text_memo))
        return binding.root
    }
}
