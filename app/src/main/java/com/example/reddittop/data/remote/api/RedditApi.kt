package com.example.reddittop.data.remote.api

import com.example.reddittop.data.remote.model.RedditResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {
    @GET("/top.json")
    suspend fun getTopPosts(
        @Query("t") time: String,
        @Query("after") after: String?,
        @Query("before") before: String?,
        @Query("count") count: Int,
    ): RedditResponse
}