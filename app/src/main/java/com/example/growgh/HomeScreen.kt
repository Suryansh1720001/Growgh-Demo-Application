package com.example.growgh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat


class HomeScreen : AppCompatActivity() {


private lateinit var btnUpload_Image: TextView
private lateinit var fetch_Image: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        btnUpload_Image = findViewById(R.id.btnUploadImage)
        fetch_Image = findViewById(R.id.btnfeed)




        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR

        window.statusBarColor = ContextCompat.getColor(this, R.color.white)


        fetch_Image.setOnClickListener{
            startActivity(Intent
                (this@HomeScreen,ImageShowcase::class.java))

        }
        btnUpload_Image.setOnClickListener{
            startActivity(Intent
                (this@HomeScreen,Upload_Image::class.java))

        }

    }
}