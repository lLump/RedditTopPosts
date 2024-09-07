package com.example.reddittop.data.model

data class RedditResponse(
    val data: RedditData
)

data class RedditData(
    val children: List<RedditChildren>
)

data class RedditChildren(
    val data: RedditPostResponse
)

data class RedditPostResponse(
    val author_fullname: String,
    val created_utc: Long,
    val thumbnail: String?,
    val num_comments: Int
)