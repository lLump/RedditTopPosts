package com.example.reddittop.di

import android.app.Application
import androidx.room.Room
import com.example.reddittop.data.local.dao.AppDatabase
import com.example.reddittop.di.containers.ApiContainer
import com.example.reddittop.di.containers.ImageContainer
import com.example.reddittop.di.containers.RoomContainer

class MyApplication: Application() {
    lateinit var apiContainer: ApiContainer
    lateinit var imageContainer: ImageContainer
    lateinit var roomContainer: RoomContainer

    override fun onCreate() {
        super.onCreate()
        initContainers()
        initRoom()
    }

    private fun initContainers() {
        apiContainer = ApiContainer()
        imageContainer = ImageContainer().apply {
            setUrlOpener(this@MyApplication)
            setImageDownloader(this@MyApplication)
        }
        roomContainer = RoomContainer()
    }

    private fun initRoom() {
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "local_reddit_db"
        ).build()
        roomContainer.initRepository(database)
    }
}