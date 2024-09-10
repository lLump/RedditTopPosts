package com.example.reddittop.data.remote.repository

import android.util.Log
import com.example.reddittop.data.remote.api.RedditApi
import com.example.reddittop.domain.model.RedditTime
import com.example.reddittop.data.remote.model.childrenToPosts
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
    ): Pair<List<RedditPost>, String> {
        return try {
            val data = api.getTopPosts(
                time = time(),
                after = after,
                before = before,
                count = count,
            ).data
            return Pair(data.childrenToPosts(), data.after)
        } catch (e: Exception) {
            Log.e(tag, e.stackTraceToString())
            Pair(listOf(), "")
        }
    }
}