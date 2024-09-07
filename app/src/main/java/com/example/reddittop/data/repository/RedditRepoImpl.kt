package com.example.reddittop.data.repository

import android.util.Log
import com.example.reddittop.data.api.RedditApi
import com.example.reddittop.data.api.RedditTime
import com.example.reddittop.data.model.parseChildrenToPosts
import com.example.reddittop.domain.model.RedditPost
import com.example.reddittop.domain.repository.RedditRepo

class RedditRepoImpl(private val api: RedditApi) : RedditRepo {
    private val tag = "API_ERROR" // В идеале выносить, к примеру в абстрактный репозиторий,
                                  // если имееться. Или в константу, но не в данном случае
    override suspend fun getTopPosts(
                                      time: RedditTime,
                                      after: String?,
                                      before: String?,
                                      count: Int,
    ): List<RedditPost> {
        return try {
            val response = api.getTopPosts(
                time = time(),
                after = after,
                before = before,
                count = count,
            )
            return response.data.parseChildrenToPosts()
        } catch (e: Exception) {
            Log.e(tag, e.stackTraceToString())
            listOf()
        }
    }
}