package com.example.growgh

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ImageShowcase : AppCompatActivity() {
    private lateinit var unsplashApi: UnsplashApi
    private lateinit var photoAdapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            setContentView(R.layout.activity_image_showcase)


            val refreshButton: ImageView = findViewById(R.id.refreshButton)
            refreshButton.setOnClickListener {
                fetchPhotos()
            }


            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR


            window.statusBarColor = ContextCompat.getColor(this, R.color.statusBarColorBlue)


            val gson: Gson = GsonBuilder().create()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            unsplashApi = retrofit.create(UnsplashApi::class.java)
            photoAdapter = PhotoAdapter()

            val recyclerView: RecyclerView = findViewById(R.id.imageRecyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = photoAdapter

            fetchPhotos()


        }



    private fun fetchPhotos() {
        val apiKey = "lvrUxqV9Oq-DE4jmAfkXq6ZuR5cruY35DI5oN-mxB04" // Replace with your actual API key

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = unsplashApi.getPhotos(10, apiKey)

                withContext(Dispatchers.Main) {
                    photoAdapter.setPhotos(response)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@ImageShowcase, "Unable to load", Toast.LENGTH_LONG).show()
            }
        }
    }
}
