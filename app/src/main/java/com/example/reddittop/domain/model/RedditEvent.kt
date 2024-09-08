package com.example.reddittop.domain.model

sealed interface RedditEvent {
    data object RefreshScreen : RedditEvent
    data object LoadPosts : RedditEvent
    data class LoadNextPosts(val after: String, val count: Int) : RedditEvent
}