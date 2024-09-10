package com.example.reddittop.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reddittop.data.local.model.RedditAfterEntity
import com.example.reddittop.data.local.model.RedditPostEntity

@Database(entities = [RedditPostEntity::class, RedditAfterEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun postsDao(): RedditPostsDao
    abstract fun afterDao(): RedditAfterDao
}