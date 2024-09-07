package com.example.reddittop.data.model

import com.example.reddittop.domain.model.RedditPost
import java.util.concurrent.TimeUnit

fun RedditData.parseChildrenToPosts(): List<RedditPost> {
    val childrenList = this.children
    val posts = childrenList.map { child ->
        child.data.toRedditPost()
    }
    return posts
}

fun RedditPostResponse.toRedditPost(): RedditPost {
    return RedditPost(
        author = author_fullname,
        createdAt = created_utc.toHoursAgo(),
        thumbnailUrl = thumbnail,
        commentsAmount = num_comments
    )
}

private fun Long.toHoursAgo(): String {
    val currentTime = System.currentTimeMillis() / 1000
    val timeDifference = currentTime - this
    val hoursAgo = TimeUnit.SECONDS.toHours(timeDifference)
    return hoursAgo.toString()
}