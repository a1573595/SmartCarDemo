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
import android.graphics.Matrix


class MainActivity : AppCompatActivity() {
    lateinit var bnve: BottomNavigationViewEx
    lateinit var tv_speed: TextView

    lateinit var wheel: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        updateText(75)
    }

    private fun initView(){
        bnve = findViewById(R.id.bnve)
        tv_speed = findViewById(R.id.tv_speed)
        wheel = resources.getDrawable(R.drawable.wheel,null)

        bnve.enableItemShiftingMode(false)
        bnve.enableShiftingMode(false)
        bnve.enableAnimation(false)
        bnve.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        fab.setOnClickListener { view -> Toast.makeText(this@MainActivity, "Center", Toast.LENGTH_SHORT).show()}

        wheel = rotate(45f)
        tv_speed.setCompoundDrawablesWithIntrinsicBounds(null, wheel, null, null)
    }

    private fun rotate(degree: Float): Drawable {
        val iconBitmap = (wheel as BitmapDrawable).bitmap

        val matrix = Matrix()
        matrix.postRotate(degree)
        val targetBitmap = Bitmap.createBitmap(iconBitmap, 0, 0, iconBitmap.width, iconBitmap.height, matrix, true)

        return BitmapDrawable(resources, targetBitmap)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.i_music -> {
                //message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.i_backup -> {
                //message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.i_favor -> {
                //message.setText(R.string.title_notifications)
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
            SeekCircle.progress = input
            tv_speed.text = SeekCircle.progress.toString()
        } catch (e: Exception) {
        }
    }
}
