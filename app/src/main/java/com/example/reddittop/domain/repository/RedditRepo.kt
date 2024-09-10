package com.example.reddittop.domain.repository

import com.example.reddittop.domain.model.RedditTime
import com.example.reddittop.domain.model.RedditPost

interface RedditRepo {
    suspend fun getTopPosts(
        time: RedditTime,
        after: String? = null,
        before: String? = null,
        count: Int,
    ) : Pair<List<RedditPost>, String>
}