package com.example.reddittop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reddittop.data.api.RedditTime
import com.example.reddittop.domain.repository.RedditRepo
import com.example.reddittop.domain.model.RedditEvent
import com.example.reddittop.domain.model.RedditEvent.*
import com.example.reddittop.presentation.state.PostsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RedditViewModel(
    private val remoteRepo: RedditRepo,
) : ViewModel() {
    private val _state = MutableStateFlow(PostsState())
    val state = _state.asStateFlow()

    fun onEvent(event: RedditEvent) {
        when (event) {
            RefreshScreen -> initialLoadPosts()
            LoadPosts -> {}
            is LoadNextPosts -> {}
        }
    }

    private fun initialLoadPosts() {
        viewModelScope.launch {
            clearState() // можно не очищать список постов в стейте, дабы они не исчезали перед появлением новых
                         // скопировав стейт и изменив только isLoading на true
            val (posts, after) = withContext(Dispatchers.IO) {
                remoteRepo.getTopPosts(
                    time = RedditTime.ALL,
//                after = null,
//                before = null,
                    count = 0,
                )
            }
            _state.emit(
                PostsState(
                    isLoading = false,
                    posts = posts
                )
            )
        }
    }

    private suspend fun clearState() {
        _state.emit(
            PostsState(
                isLoading = true,
                posts = listOf()
            )
        )
    }
}