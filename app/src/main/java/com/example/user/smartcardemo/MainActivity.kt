package com.example.user.smartcardemo

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var bnve: BottomNavigationViewEx
    lateinit var tv_speed: TextView

    lateinit var dot: Drawable

    internal var handler = Handler()
    lateinit var workRunnable: Runnable

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val CarInfo: DatabaseReference = database.getReference().child("Car_Info")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(FirebaseInstanceId.getInstance().token!=null)
            Log.d("FireBase_Token", FirebaseInstanceId.getInstance().token)

        initView()
        workRunnable = Runnable {
            img_wheel.rotation = 0f
            dot.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)
            tv_speed.setCompoundDrawablesWithIntrinsicBounds(null, dot, null, null)

            img_headlight.setColorFilter(Color.parseColor("#000000"))
            img_headlight2.setColorFilter(Color.parseColor("#000000"))
            img_warning.setColorFilter(Color.parseColor("#000000"))
            img_cardoor.setColorFilter(Color.parseColor("#000000"))
            img_abs.setColorFilter(Color.parseColor("#000000"))
            img_handbrake.setColorFilter(Color.parseColor("#000000"))
            img_brake.setColorFilter(Color.parseColor("#000000"))

            img_parking.setColorFilter(Color.parseColor("#000000"))
            img_neutral.setColorFilter(Color.parseColor("#000000"))
            img_drive.setColorFilter(Color.parseColor("#000000"))
            img_reverse.setColorFilter(Color.parseColor("#000000"))

            updateText(0)

            CarInfo.addChildEventListener(
                    object : ChildEventListener {
                        override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                        }

                        override fun onCancelled(p0: DatabaseError?) {
                        }

                        override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
                            Log.e("Database", "ChildChanged")
                            if(!dataSnapshot.hasChildren()) {
                                Log.e("Database1", "Key:"+dataSnapshot.key +"   Value:"+ dataSnapshot.value.toString())
                                when(dataSnapshot.key){
                                    "Door_lock"->{
                                        if(dataSnapshot.getValue() as Boolean)
                                            img_cardoor.setColorFilter(Color.parseColor("#EEBB11"))
                                        else
                                            img_cardoor.setColorFilter(Color.parseColor("#000000"))
                                    }
                                    "Gear"-> updateGear(Integer.valueOf(dataSnapshot.getValue().toString()))
                                    "Light"-> updateLight(Integer.valueOf(dataSnapshot.getValue().toString()))
                                    "Speed"-> updateText(Integer.valueOf(dataSnapshot.getValue().toString()))
                                    "Steering"-> img_wheel.rotation = java.lang.Float.valueOf(dataSnapshot.getValue().toString())
                                }
                            }else{
                                for (childSnapshot in dataSnapshot.children) {
                                    Log.e("Database2","Key:"+childSnapshot.key+"   Value:"+childSnapshot.getValue().toString())
                                    when(childSnapshot.key){
                                        "Brake_pedal"-> updateBrake(Integer.valueOf(childSnapshot.getValue().toString()))
                                        "Foot_brake_pedal"->{
                                            if(childSnapshot.getValue() as Boolean)
                                                img_handbrake.setColorFilter(Color.parseColor("#F0010E"))
                                            else
                                                img_handbrake.setColorFilter(Color.parseColor("#00000000"))
                                        }
                                    //"Gas_pedal"->
                                    }
                                }
                            }
                        }

                        override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                        }

                        override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                            Log.e("Database", "ChildAdded")
                            if(!dataSnapshot.hasChildren()) {
                                Log.e("Database1", "Key:"+dataSnapshot.key +"   Value:"+ dataSnapshot.value.toString())
                                when(dataSnapshot.key){
                                    "Door_lock"->{
                                        if(dataSnapshot.getValue() as Boolean)
                                            img_cardoor.setColorFilter(Color.parseColor("#EEBB11"))
                                        else
                                            img_cardoor.setColorFilter(Color.parseColor("#000000"))
                                    }
                                    "Gear"-> updateGear(Integer.valueOf(dataSnapshot.getValue().toString()))
                                    "Light"-> updateLight(Integer.valueOf(dataSnapshot.getValue().toString()))
                                    "Speed"-> updateText(Integer.valueOf(dataSnapshot.getValue().toString()))
                                    "Steering"-> img_wheel.rotation = java.lang.Float.valueOf(dataSnapshot.getValue().toString())
                                }
                            }else{
                                for (childSnapshot in dataSnapshot.children) {
                                    Log.e("Database2","Key:"+childSnapshot.key+"   Value:"+childSnapshot.getValue().toString())
                                    when(childSnapshot.key){
                                        "Brake_pedal"-> updateBrake(Integer.valueOf(childSnapshot.getValue().toString()))
                                        "Foot_brake_pedal"->{
                                            if(childSnapshot.getValue() as Boolean)
                                                img_handbrake.setColorFilter(Color.parseColor("#F0010E"))
                                            else
                                                img_handbrake.setColorFilter(Color.parseColor("#00000000"))
                                        }
                                    //"Gas_pedal"->
                                    }
                                }
                            }
                        }
                    })
        }
        reset()
    }

    private fun initView(){
        bnve = findViewById(R.id.bnve)
        tv_speed = findViewById(R.id.tv_speed)
        dot = resources.getDrawable(R.drawable.ic_dot,null)
        dot.setColorFilter(Color.parseColor("#77FF00"), PorterDuff.Mode.SRC_ATOP)

        bnve.enableItemShiftingMode(false)
        bnve.enableShiftingMode(false)
        bnve.enableAnimation(false)
        bnve.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        SeekCircle_speed.setMax(255)
        fab.setOnClickListener { /*_ ->*/
            //Toast.makeText(this@MainActivity, "Center", Toast.LENGTH_SHORT).show()
            reset()
        }
    }

    private fun reset(){
        img_wheel.rotation = 45f
        dot.setColorFilter(Color.parseColor("#77FF00"), PorterDuff.Mode.SRC_ATOP)
        tv_speed.setCompoundDrawablesWithIntrinsicBounds(null, dot, null, null)

        img_headlight.setColorFilter(Color.parseColor("#EEBB11"))
        img_headlight2.setColorFilter(Color.parseColor("#EEBB11"))
        img_warning.setColorFilter(Color.parseColor("#F0010E"))
        img_cardoor.setColorFilter(Color.parseColor("#EEBB11"))
        img_abs.setColorFilter(Color.parseColor("#EEBB11"))
        img_handbrake.setColorFilter(Color.parseColor("#F0010E"))
        img_brake.setColorFilter(Color.parseColor("#F0010E"))

        img_parking.setColorFilter(Color.parseColor("#EEBB11"))
        img_neutral.setColorFilter(Color.parseColor("#EEBB11"))
        img_drive.setColorFilter(Color.parseColor("#EEBB11"))
        img_reverse.setColorFilter(Color.parseColor("#EEBB11"))

        updateText(255)

        handler.removeCallbacks(workRunnable)
        handler.postDelayed(workRunnable, 1000)
    }

    /*private fun rotate(degree: Float): Drawable {
        val iconBitmap = (wheel as BitmapDrawable).bitmap

        val matrix = Matrix()
        matrix.postRotate(degree)
        val targetBitmap = Bitmap.createBitmap(iconBitmap, 0, 0, iconBitmap.width, iconBitmap.height, matrix, true)

        return BitmapDrawable(resources, targetBitmap)
    }*/

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.i_visibility -> {
                ll_page.visibility = View.GONE;
                return@OnNavigationItemSelectedListener true
            }
            R.id.i_empty -> {
                return@OnNavigationItemSelectedListener false
            }
            R.id.i_backup -> {
                ll_page.visibility = View.VISIBLE;
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun updateText(input: Int) {
        try {
            SeekCircle_speed.progress = input
            tv_speed.text = SeekCircle_speed.progress.toString()
        } catch (e: Exception) {
        }
    }

    private fun updateGear(gear: Int){
        img_parking.setColorFilter(Color.parseColor("#888888"))
        img_neutral.setColorFilter(Color.parseColor("#888888"))
        img_drive.setColorFilter(Color.parseColor("#888888"))
        img_reverse.setColorFilter(Color.parseColor("#888888"))

        when(gear){
            0->img_parking.setColorFilter(Color.parseColor("#EEBB11"))
            1->img_neutral.setColorFilter(Color.parseColor("#EEBB11"))
            2->img_drive.setColorFilter(Color.parseColor("#EEBB11"))
            3->img_reverse.setColorFilter(Color.parseColor("#EEBB11"))
        }
    }

    private fun updateLight(light: Int){
        img_headlight.setColorFilter(Color.parseColor("#00000000"))
        img_headlight2.setColorFilter(Color.parseColor("#00000000"))
        img_warning.setColorFilter(Color.parseColor("#00000000"))

        when(light){
            1->img_headlight.setColorFilter(Color.parseColor("#EEBB11"))
            2->img_headlight2.setColorFilter(Color.parseColor("#EEBB11"))
            3->img_warning.setColorFilter(Color.parseColor("#F0010E"))
        }
    }

    private fun updateBrake(brake: Int){
        img_brake.setColorFilter(Color.parseColor("#00000000"))
        img_abs.setColorFilter(Color.parseColor("#00000000"))

        when(brake){
            1->img_brake.setColorFilter(Color.parseColor("#F0010E"))
            2->img_brake.setColorFilter(Color.parseColor("#FF0000"))
            3->img_abs.setColorFilter(Color.parseColor("#EEBB11"))
        }
    }
}
