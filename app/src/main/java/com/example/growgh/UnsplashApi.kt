package com.example.growgh

import retrofit2.http.GET
import retrofit2.http.Query


interface UnsplashApi {
    @GET("photos/random")
    suspend fun getPhotos(
        @Query("count") count: Int,
        @Query("client_id") apiKey: String
    ): List<UnsplashPhoto>
}
