package com.example.reddittop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reddittop.data.api.RedditTime
import com.example.reddittop.domain.repository.RedditRepo
import com.example.reddittop.domain.model.RedditEvent
import com.example.reddittop.domain.model.RedditEvent.*
import com.example.reddittop.presentation.state.PostsState
import com.example.reddittop.presentation.viewmodel.model.RequestInfo
import com.example.reddittop.presentation.viewmodel.model.addNewInfo
import com.example.reddittop.presentation.viewmodel.model.clearInfo
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

    private var requestInfo = RequestInfo()

    fun onEvent(event: RedditEvent) {
        when (event) {
            RefreshScreen -> viewModelScope.launch {
                requestInfo.clearInfo()
                prepareStateToRefresh()
                initialLoadPosts()
            }
            LoadPosts -> viewModelScope.launch {
                initialLoadPosts()
            }
            LoadNextPosts -> viewModelScope.launch {
                if (!_state.value.isLoading) { // предотвращает двойной некст запрос
                    // который случается если листнуть при загрузке, в самом низу, быстро вверх-вниз
                    prepareStateToNextLoad()
                    loadNextPosts()
                }
            }
        }
    }

    private suspend fun initialLoadPosts() {
        val (posts, after) = getPosts()
        _state.emit(
            PostsState(
                isRefreshing = false,
                isLoading = false,
                posts = posts
            )
        )
        requestInfo.addNewInfo(
            newAfter = after,
            count = posts.count()
        )
    }

    private suspend fun loadNextPosts() {
        val (newPosts, after) = getPosts()
        requestInfo.addNewInfo(
            newAfter = after,
            count = newPosts.count()
        )
        _state.emit(
            _state.value.copy(
                posts = _state.value.posts + newPosts,
                isLoading = false
            )
        )
    }

    private suspend fun getPosts() = withContext(Dispatchers.IO) {
        remoteRepo.getTopPosts(
            time = RedditTime.ALL,
            after = requestInfo.after,
            count = requestInfo.itemsLoaded,
        )
    }

    private suspend fun prepareStateToNextLoad() {
        _state.emit(
            _state.value.copy(
                isRefreshing = false,
                isLoading = true,
            )
        )
    }

    private suspend fun prepareStateToRefresh() {
        _state.emit(
            PostsState(
                isRefreshing = true,
                isLoading = false,
                posts = listOf() // можно не очищать список постов в стейте, дабы они не исчезали
                                 // перед появлением новых, скопировав стейт
            )
        )
    }
}