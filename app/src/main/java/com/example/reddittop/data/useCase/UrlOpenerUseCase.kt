package com.example.reddittop.data.useCase

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.reddittop.domain.useCase.UrlOpener

class UrlOpenerUseCase(private val context: Context): UrlOpener {
    override operator fun invoke(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}