package com.example.refrigerator

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.refrigerator.databinding.FragmentHomeBinding
import com.example.refrigerator.databinding.FragmentTab1Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {
    var arrayList: ArrayList<Any> = ArrayList()
    lateinit var binding: FragmentHomeBinding

    //type 저장 변수
    var ingredientName: ArrayList<String> = ArrayList()
    var ingredientAmount: ArrayList<String> = ArrayList()
    var ingredientTime: ArrayList<String> = ArrayList()

    lateinit var ingredientTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        ingredientTextView = binding.ingredient

        var firestore: FirebaseFirestore? = null
        var uid: String? = null
        uid = FirebaseAuth.getInstance().currentUser?.uid

        firestore = FirebaseFirestore.getInstance();
        firestore.collection("sourcefile").document("ingredients")
            .addSnapshotListener { value, error ->
                Log.d("event", value.toString())
                arrayList.clear()
                arrayList.addAll((value?.data?.get("ingredient") as ArrayList<*>))

                //배열에 저장
                for (i in 0 until arrayList.size) {
                    try {
                        if (i % 3 == 0)
                            ingredientName.add(arrayList[i] as String)
                        else if (i % 3 == 1)
                            ingredientTime.add(arrayList[i] as String)
                        if (i % 3 == 2)
                            ingredientAmount.add(arrayList[i] as String)
                    } catch (e: Exception) {
                        Log.d("error:$i", e.toString())
                    }
                }

                ingredientTextView.text = ingredientTime.toString()

                Log.d("event", arrayList.toString())
            }
        return binding.root
    }

}