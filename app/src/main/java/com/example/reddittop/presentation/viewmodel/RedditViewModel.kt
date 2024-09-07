package com.example.reddittop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reddittop.data.api.RedditTime
import com.example.reddittop.domain.repository.RedditRepo
import com.example.reddittop.presentation.viewmodel.model.RedditEvent
import com.example.reddittop.presentation.viewmodel.model.RedditEvent.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RedditViewModel(
    private val remoteRepo: RedditRepo,
): ViewModel()  {
    fun onEvent(event: RedditEvent) {
        when (event) {
            RefreshScreen -> initialLoadPosts()
            LoadPosts -> {}
            is LoadNextPosts -> {}
        }
    }

    private fun initialLoadPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            remoteRepo.getTopPosts(
                time = RedditTime.ALL,
//                after = null,
//                before = null,
                count = 0,
            )
        }
    }
}