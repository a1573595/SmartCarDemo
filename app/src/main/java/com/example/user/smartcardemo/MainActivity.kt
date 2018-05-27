package com.example.user.smartcardemo

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.widget.TextView
import android.widget.Toast

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx

import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.util.Log

import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.database.DataSnapshot

class MainActivity : AppCompatActivity() {
    lateinit var bnve: BottomNavigationViewEx
    lateinit var tv_speed: TextView

    //lateinit var wheel: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        updateText(75)

        if(FirebaseInstanceId.getInstance().token!=null)
            Log.d("FireBase_Token", FirebaseInstanceId.getInstance().token)
        val database:FirebaseDatabase = FirebaseDatabase.getInstance()
        val CarInfo:DatabaseReference = database.getReference().child("Info")
        CarInfo.addChildEventListener(
                object : ChildEventListener {
                    override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                    }

                    override fun onCancelled(p0: DatabaseError?) {
                    }

                    override fun onChildChanged(dataSnapshot: DataSnapshot, p1: String?) {
                        Log.e("Database", "ChildChanged")
                        if(!dataSnapshot.hasChildren()) {
                            Log.e("Database", "Key:"+dataSnapshot.key +"   Value:"+ dataSnapshot.value.toString())
                        }else{
                            for (childSnapshot in dataSnapshot.children) {
                                Log.e("Database","Key:"+childSnapshot.key+"   Value:"+childSnapshot.getValue().toString())
                            }
                        }
                    }

                    override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                    }

                    override fun onChildAdded(dataSnapshot: DataSnapshot, p1: String?) {
                        Log.e("Database", "ChildAdded")
                        if(!dataSnapshot.hasChildren()) {
                            Log.e("Database", "Key:"+dataSnapshot.key +"   Value:"+ dataSnapshot.value.toString())
                        }else{
                            for (childSnapshot in dataSnapshot.children) {
                                Log.e("Database","Key:"+childSnapshot.key+"   Value:"+childSnapshot.getValue().toString())
                                when(childSnapshot.key){
                                    "Door_lock"->
                                        if((childSnapshot.getValue() is Boolean))
                                            img_cardoor.setColorFilter(Color.parseColor("#FFFF00"))
                                        else
                                            img_cardoor.setColorFilter(Color.parseColor("#000000"))

                                }
                            }
                        }
                    }
                })
    }

    private fun initView(){
        bnve = findViewById(R.id.bnve)
        tv_speed = findViewById(R.id.tv_speed)
        //wheel = resources.getDrawable(R.drawable.wheel,null)

        bnve.enableItemShiftingMode(false)
        bnve.enableShiftingMode(false)
        bnve.enableAnimation(false)
        bnve.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        fab.setOnClickListener { _ -> Toast.makeText(this@MainActivity, "Center", Toast.LENGTH_SHORT).show()}

        img_wheel.rotation = 45f
        //wheel = rotate(45f)
        //tv_speed.setCompoundDrawablesWithIntrinsicBounds(null, wheel, null, null)
        img_headlight.setColorFilter(Color.parseColor("#FFFF00"))
        img_headlight2.setColorFilter(Color.parseColor("#FFFF00"))
        img_warning.setColorFilter(Color.parseColor("#FF0000"))
        img_cardoor.setColorFilter(Color.parseColor("#FFFF00"))
        img_abs.setColorFilter(Color.parseColor("#FFFF00"))
        img_handbrake.setColorFilter(Color.parseColor("#FF0000"))
        img_brake.setColorFilter(Color.parseColor("#FF0000"))

        img_parking.setColorFilter(Color.parseColor("#FF8800"))
        img_neutral.setColorFilter(Color.parseColor("#888888"))
        img_drive.setColorFilter(Color.parseColor("#888888"))
        img_reverse.setColorFilter(Color.parseColor("#888888"))
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
            R.id.i_backup -> {
                //message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.i_visibility -> {
                //message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
            R.id.i_empty -> {
                //message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener false
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
}
