package com.example.reddittop.di

import android.app.Application
import com.example.reddittop.data.useCase.UrlOpenerUseCase
import com.example.reddittop.domain.useCase.UrlOpener

class MyApplication: Application() {
    lateinit var apiContainer: ApiContainer
    lateinit var imageContainer: ImageContainer

    override fun onCreate() {
        super.onCreate()
        apiContainer = ApiContainer()
        imageContainer = ImageContainer().apply {
            setUrlOpener(this@MyApplication)
            setImageDownloader(this@MyApplication)
        }
    }
}