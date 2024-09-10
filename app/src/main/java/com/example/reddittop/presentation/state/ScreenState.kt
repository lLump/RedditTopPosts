package com.example.reddittop.presentation.state

import com.example.reddittop.domain.model.RedditPost

data class ScreenState (
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = true,
    val posts: List<RedditPost> = listOf(),
)