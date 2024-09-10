package com.example.reddittop.di.containers

import com.example.reddittop.data.remote.api.RedditApi
import com.example.reddittop.data.remote.repository.RedditRepoImpl
import com.example.reddittop.domain.repository.RedditRepo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiContainer {

    private fun retrofit() = Retrofit.Builder()
        .baseUrl("https://www.reddit.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val redditRepo: RedditRepo = RedditRepoImpl(api = retrofit().create(RedditApi::class.java))
}