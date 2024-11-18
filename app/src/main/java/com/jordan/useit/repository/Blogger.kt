package com.jordan.useit.repository

import com.jordan.useit.repository.services.IBlogService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Blogger {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var blogService: IBlogService = retrofit.create(IBlogService::class.java)
}
