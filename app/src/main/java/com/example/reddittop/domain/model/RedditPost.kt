package com.example.reddittop.domain.model

data class RedditPost (
    val author: String,
    val createdAt: String,
    val thumbnailUrl: String?,
    val commentsAmount: Int,
)