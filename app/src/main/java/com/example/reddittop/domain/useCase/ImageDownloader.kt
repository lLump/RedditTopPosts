package com.example.reddittop.domain.useCase

interface ImageDownloader {
    suspend operator fun invoke(url: String): Boolean
}