package com.example.reddittop.domain.model

data class RedditPost (
    val author: String,
    val theme: String,
    val timeAgo: String,
    val thumbnailUrl: String?,
    val commentsAmount: Int,
)