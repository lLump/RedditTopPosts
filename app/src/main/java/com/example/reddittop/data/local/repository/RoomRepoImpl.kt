package com.example.reddittop.data.local.repository

import com.example.reddittop.data.local.dao.RedditAfterDao
import com.example.reddittop.data.local.dao.RedditPostsDao
import com.example.reddittop.data.local.model.RedditAfterEntity
import com.example.reddittop.data.local.model.toRedditPost
import com.example.reddittop.data.local.model.toRedditPostEntity
import com.example.reddittop.domain.model.RedditPost
import com.example.reddittop.domain.repository.RoomRepo

class RoomRepoImpl(
    private val postsDao: RedditPostsDao,
    private val afterDao: RedditAfterDao,
) : RoomRepo {
    override suspend fun getInfo(): Pair<List<RedditPost>, String?> {
        val posts = postsDao.getAllPosts()?.map { it.toRedditPost() }
        val after = afterDao.getAfter()?.after
        return Pair(posts ?: listOf(), after)
    }

    override suspend fun insertInfo(posts: List<RedditPost>, after: String) {
        val dto = posts.map { it.toRedditPostEntity() }
        postsDao.replacePosts(dto)
        afterDao.insertAfter(RedditAfterEntity(after))
    }

    override suspend fun clearDB() {
        postsDao.clearPosts()
        afterDao.clearAfter()
    }
}