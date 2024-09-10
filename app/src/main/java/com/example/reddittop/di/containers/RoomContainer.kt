package com.example.reddittop.di.containers

import com.example.reddittop.data.local.dao.AppDatabase
import com.example.reddittop.data.local.repository.RoomRepoImpl
import com.example.reddittop.domain.repository.RoomRepo

class RoomContainer {
    lateinit var roomRepo: RoomRepo

    fun initRepository(roomDB: AppDatabase) {
        roomRepo = RoomRepoImpl(
            postsDao = roomDB.postsDao(),
            afterDao = roomDB.afterDao()
        )
    }
}