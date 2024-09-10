package com.example.reddittop.data.local.model

import com.example.reddittop.domain.model.RedditPost

fun RedditPostEntity.toRedditPost() : RedditPost {
    return RedditPost(
        author = this.author,
        theme = this.theme,
        timeAgo = this.timeAgo,
        thumbnailUrl = this.thumbnailUrl,
        commentsAmount = this.commentsAmount
    )
}

fun RedditPost.toRedditPostEntity() : RedditPostEntity {
    return RedditPostEntity(
        author = this.author,
        theme = this.theme,
        timeAgo = this.timeAgo,
        thumbnailUrl = this.thumbnailUrl,
        commentsAmount = this.commentsAmount
    )
}