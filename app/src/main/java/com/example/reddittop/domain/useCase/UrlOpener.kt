package com.example.reddittop.domain.useCase

interface UrlOpener {
    operator fun invoke(url: String)
}