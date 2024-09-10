package com.example.reddittop.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.reddittop.data.local.model.RedditAfterEntity
import com.example.reddittop.data.local.model.RedditPostEntity

@Dao
interface RedditPostsDao {
    @Query("SELECT * FROM reddit_post")
    suspend fun getAllPosts(): List<RedditPostEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<RedditPostEntity>)

    @Query("DELETE FROM reddit_post")
    suspend fun clearPosts()

    @Transaction
    suspend fun replacePosts(posts: List<RedditPostEntity>) {
        clearPosts()
        insertPosts(posts)
    }
}

@Dao
interface RedditAfterDao {
    @Query("SELECT [after] FROM reddit_after LIMIT 1")
    suspend fun getAfter(): RedditAfterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAfter(after: RedditAfterEntity)

    @Query("DELETE FROM reddit_after")
    suspend fun clearAfter()
}