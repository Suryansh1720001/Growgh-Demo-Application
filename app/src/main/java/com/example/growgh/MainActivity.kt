package com.example.growgh


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager


class MainActivity : AppCompatActivity(), ViewPagerAdapter.ViewPagerAdapterListener{



    private lateinit var mSlideViewPager: ViewPager
    private lateinit var next: FrameLayout
    private lateinit var skipbtn: ImageView

    private lateinit var dots: Array<TextView>
    private lateinit var viewPagerAdapter: ViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the application is running for the first time
        val preferences = getPreferences(Context.MODE_PRIVATE)
        val isFirstRun = preferences.getBoolean("isFirstRun", true)
        if (isFirstRun) {
            setContentView(R.layout.activity_main)

            skipbtn = findViewById(R.id.skipButton)


            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR


            window.statusBarColor = ContextCompat.getColor(this, R.color.white)


            skipbtn.setOnClickListener {
                val i = Intent(this@MainActivity, HomeScreen::class.java)
                startActivity(i)
                finish()
            }

            mSlideViewPager = findViewById(R.id.slideViewPager)


            viewPagerAdapter = ViewPagerAdapter(this)


            mSlideViewPager.adapter = viewPagerAdapter

            viewPagerAdapter.setListener(this)

            // Set a flag indicating that the splash screen has been shown
            val editor = preferences.edit()
            editor.putBoolean("isFirstRun", false)
            editor.apply()

        } else {
            // If it's not the first run, directly start the MainActivity
            val intent = Intent(this@MainActivity, HomeScreen::class.java)
            startActivity(intent)
            finish()

        }

    }

    private fun getItem(i: Int): Int {

        return mSlideViewPager.currentItem + i

    }

    override fun onReadyButtonClick() {
        if (getItem(0) < 2)
            mSlideViewPager.setCurrentItem(getItem(1), true)
        else {
            val i = Intent(this@MainActivity, HomeScreen::class.java)
            startActivity(i)
            finish()
        }
    }


}
