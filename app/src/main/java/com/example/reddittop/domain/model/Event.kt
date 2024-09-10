package com.example.reddittop.domain.model

sealed interface Event

sealed interface RedditEvent: Event {
    data object RefreshScreen : RedditEvent
    data object LoadPosts : RedditEvent
    data object LoadNextPosts : RedditEvent
}

sealed interface MediaEvent: Event {
    data class OpenImage(val url: String) : MediaEvent
    data class SaveImage(val url: String) : MediaEvent
}