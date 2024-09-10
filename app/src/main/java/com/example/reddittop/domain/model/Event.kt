package com.example.reddittop.domain.model

sealed interface Event

sealed interface RedditEvent: Event {
    data object RefreshScreen : RedditEvent
    data object LoadNextPosts : RedditEvent
    data class ChangeTimeDiapason(val time: RedditTime) : RedditEvent
}

sealed interface MediaEvent: Event {
    data class OpenImage(val url: String) : MediaEvent
    data class SaveImage(val url: String) : MediaEvent
}

sealed interface RoomEvent: Event {
    data object SaveInfo: RoomEvent
    data object RestoreInfo: RoomEvent
}