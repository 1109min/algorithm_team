package com.example.refrigerator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.refrigerator.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {
    private val viewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var fab_main: FloatingActionButton? = null
    private  var fab_sub1: ConstraintLayout? = null
    private  var fab_sub2: ConstraintLayout? = null
    private var fab_open: Animation? = null
    private  var fab_close:Animation? = null
    private var isFabOpen = false

    lateinit var fadeInAnim: Animation
    lateinit var fadeOutAnim: Animation
    lateinit var mbd: Animation
    lateinit var mbu: Animation


    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    fun gohome(){
        viewBinding.navBottom.run{

                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.containerFragment.id,HomeFragment())
                            .commitAllowingStateLoss()
            selectedItemId = R.id.menu_home
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        var mContext = getApplicationContext()


        supportFragmentManager
            .beginTransaction()
            .replace(viewBinding.containerFragment.id,HomeFragment()) //add??? ?????? ?????????
            .commitAllowingStateLoss() //?????? commit?????? ????????? ?????? ??????

        var fragment_home = HomeFragment()
        var bundle_home = Bundle()
        var results : ArrayList<ResultData> = arrayListOf()

        fragment_home.arguments = bundle_home

        viewBinding.navBottom.run{
            setOnItemSelectedListener {
                when(it.itemId){
                    R.id.menu_home -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.containerFragment.id,HomeFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_list -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.containerFragment.id,ListFragment())
                            .commitAllowingStateLoss()

                    }
                    R.id.menu_settings -> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(viewBinding.containerFragment.id, SettingFragment())
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            selectedItemId = R.id.menu_home
        }

        fab_open = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(mContext, R.anim.fab_close);
        fab_main = viewBinding.mainBtn
        fab_sub1 = viewBinding.recommandBtn
        fab_sub2 = viewBinding.plussBtn

        fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        fadeOutAnim = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        mbd = AnimationUtils.loadAnimation(this,R.anim.move_bottom_down)
        mbu = AnimationUtils.loadAnimation(this,R.anim.move_bottom_up)

        fab_main!!.setOnClickListener{
            toggleFab()
        }
        val intent = Intent(this, Add1Activity::class.java)
        val intent2 = Intent(this, Add2Activity::class.java)
        val intentmake = Intent(this, MakeActivity::class.java)
        intent2.putExtra("test","test2")
        //????????????????????????
        fab_sub1!!.setOnClickListener{
            resultLauncher.launch(intentmake)
        }

        fab_sub2!!.setOnClickListener {

        }
        viewBinding.menuMake.setOnClickListener{
            resultLauncher.launch(intentmake)
        }

        viewBinding.recipePlus.setOnClickListener {
            resultLauncher.launch(intent)
        }
        viewBinding.ingredientPlus.setOnClickListener{
            resultLauncher.launch(intent2)
        }

        viewBinding.btnBackLayout.setOnClickListener {
            toggleFab()
            viewBinding.btnBackLayout.visibility = GONE
        }


        var firestore: FirebaseFirestore? = null
        var uid: String? = null
        uid = FirebaseAuth.getInstance().currentUser?.uid
        firestore = FirebaseFirestore.getInstance()



        var recipes : ArrayList<RecipeData> = arrayListOf()
        firestore?.collection("recipes")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            // ArrayList ?????????
            recipes.clear()

            for (snapshot in querySnapshot!!.documents) {
                var item = snapshot.toObject(RecipeData::class.java)
                recipes.add(item!!)
            }
        }


        var ingredients : ArrayList<IngredientData> = arrayListOf()
        firestore = FirebaseFirestore.getInstance()
        firestore?.collection("recipes")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            // ArrayList ?????????
            ingredients.clear()

            for (snapshot in querySnapshot!!.documents) {
                var item = snapshot.toObject(IngredientData::class.java)
                ingredients.add(item!!)
            }
        }




        //????????? ????????????
        var menuList : ArrayList<RecipeData> = arrayListOf()
        var ingredient : ArrayList<IngredientData> = arrayListOf()

        firestore = FirebaseFirestore.getInstance()

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK) {

            }
        }

    }
    private fun toggleFab() {
        if (isFabOpen) {
            fab_main!!.setImageResource(R.drawable.ic_baseline_add_24)
            fab_sub1!!.startAnimation(fab_close)
            fab_sub2!!.startAnimation(fab_close)
            viewBinding.menuMake!!.isClickable = false
            viewBinding.recipePlus!!.isClickable = false
            viewBinding.ingredientPlus!!.isClickable = false

            fab_sub1!!.isClickable = false
            fab_sub2!!.isClickable = false
            isFabOpen = false
            viewBinding.btnBackLayout.startAnimation(fadeOutAnim)
            viewBinding.btnBackLayout.visibility = GONE

        } else {
            fab_main!!.setImageResource(R.drawable.ic_baseline_close_24)
            fab_sub1!!.startAnimation(fab_open)
            fab_sub2!!.startAnimation(fab_open)
            viewBinding.menuMake!!.isClickable = true
            viewBinding.recipePlus!!.isClickable = true
            viewBinding.ingredientPlus!!.isClickable = true

            fab_sub1!!.isClickable = true
            fab_sub2!!.isClickable = true
            isFabOpen = true
            viewBinding.btnBackLayout.startAnimation(fadeInAnim)
            viewBinding.btnBackLayout.visibility = VISIBLE

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onResume() {
        fab_main!!.setImageResource(R.drawable.ic_baseline_add_24)
        fab_sub1!!.startAnimation(fab_close)
        fab_sub2!!.startAnimation(fab_close)
        viewBinding.menuMake!!.isClickable = false
        viewBinding.recipePlus!!.isClickable = false
        viewBinding.ingredientPlus!!.isClickable = false

        fab_sub1!!.isClickable = false
        fab_sub2!!.isClickable = false
        isFabOpen = false
        viewBinding.btnBackLayout.startAnimation(fadeOutAnim)
        viewBinding.btnBackLayout.visibility = GONE

        super.onResume()
    }
}


