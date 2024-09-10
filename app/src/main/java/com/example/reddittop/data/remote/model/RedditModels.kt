package com.example.reddittop.data.remote.model

data class RedditResponse(
    val data: RedditData
)

data class RedditData(
    val after: String,
    val children: List<RedditChildren>
)

data class RedditChildren(
    val data: RedditPostResponse
)

data class RedditPostResponse(
    val author_fullname: String?, //sometimes this field is null
    val subreddit: String,
    val created_utc: Long,
    val thumbnail: String?,
    val num_comments: Int
)