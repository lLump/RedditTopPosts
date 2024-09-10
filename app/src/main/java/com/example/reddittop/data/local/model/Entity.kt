package com.example.reddittop.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reddit_post")
data class RedditPostEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "theme") val theme: String,
    @ColumnInfo(name = "time_ago") val timeAgo: String,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String?,
    @ColumnInfo(name = "comments_amount") val commentsAmount: Int
)

@Entity(tableName = "reddit_after")
data class RedditAfterEntity(
    @PrimaryKey @ColumnInfo(name = "after") val after: String
)