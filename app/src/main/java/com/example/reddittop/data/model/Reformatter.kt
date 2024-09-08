package com.example.reddittop.data.model

import com.example.reddittop.domain.model.RedditPost
import java.util.concurrent.TimeUnit

fun RedditData.childrenToPosts(): List<RedditPost> {
    return this.children.map { child ->
        child.data.toRedditPost()
    }
}

fun RedditPostResponse.toRedditPost(): RedditPost {
    return RedditPost(
        author = author_fullname ?: "Guest",
        theme = subreddit,
        timeAgo = created_utc.toHoursAgo(),
        thumbnailUrl = thumbnail,
        commentsAmount = num_comments
    )
}

private fun Long.toHoursAgo(): String {
    val currentTime = System.currentTimeMillis() / 1000
    val timeDifference = currentTime - this

    val minutesAgo = TimeUnit.SECONDS.toMinutes(timeDifference)
    val hoursAgo = TimeUnit.SECONDS.toHours(timeDifference)
    val daysAgo = TimeUnit.SECONDS.toDays(timeDifference)
    return when {
        timeDifference in 61..3599 -> "$minutesAgo minutes ago"
        timeDifference in 3600..86399 -> "$hoursAgo hours ago"
        timeDifference > 86400 -> "${daysAgo}d ${hoursAgo % 24}h ago"
        else -> "ERROR"
    }
}