package com.example.reddittop.di.containers

import android.content.Context
import com.example.reddittop.data.useCase.ImageDownloaderImpl
import com.example.reddittop.data.useCase.UrlOpenerUseCase
import com.example.reddittop.domain.useCase.ImageDownloader
import com.example.reddittop.domain.useCase.UrlOpener

class ImageContainer {
    lateinit var urlOpener: UrlOpener
    lateinit var imageDownloader: ImageDownloader

    fun setUrlOpener(context: Context) {
        urlOpener = UrlOpenerUseCase(context)
    }

    fun setImageDownloader(context: Context) {
        imageDownloader = ImageDownloaderImpl(context)
    }
}