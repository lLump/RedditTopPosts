package com.example.reddittop.domain.repository

import com.example.reddittop.domain.model.RedditPost

interface RoomRepo {
    suspend fun getInfo(): Pair<List<RedditPost>, String?>
    suspend fun insertInfo(posts: List<RedditPost>, after: String)
    suspend fun clearDB()
}