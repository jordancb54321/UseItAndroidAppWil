package com.jordan.useit.repository.services

import com.jordan.useit.repository.data.Posts
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface IBlogService {
    @GET("/blogger/v3/blogs/1233286428209959450/posts")
    fun listPosts(@Query("key") key: String? = "AIzaSyB7g3ILvMqnvWHx3dldq1Bok9X8mzQOMbk",
                  @Query("fetchImages")
                  fetchImages: Boolean? = true,
                  @Query("fetchBodies") fetchBodies: Boolean? = true): Call<Posts?>?
}
