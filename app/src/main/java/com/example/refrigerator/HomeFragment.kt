package com.example.refrigerator

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    var arrayList: ArrayList<Any> = ArrayList()

    //type 저장 변수
    var ingredientName: ArrayList<String> = ArrayList()
    var ingredientAmount: ArrayList<String> = ArrayList()
    var ingredientTime: ArrayList<String> = ArrayList()

    lateinit var ingredientTextView: TextView

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflater.inflate(R.layout.fragment_home, container, false)
        ingredientTextView = binding.findViewById(R.id.ingredient)
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
                            ingredientTime.add(arrayList[i].toString())
                        if (i % 3 == 2)
                            ingredientAmount.add(arrayList[i] as String)
                    } catch (e: Exception) {
                        Log.d("error:$i", e.toString())
                    }
                }

                ingredientTextView.text = ingredientTime.toString()
//                arrayList.add(35, "2000")
//                firestore.collection("sourcefile").document("ingredients")
//                    .update("ingredient", arrayList)

                Log.d("event", arrayList.toString())
            }
        return binding
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}