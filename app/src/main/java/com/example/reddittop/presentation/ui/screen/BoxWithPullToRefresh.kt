package com.example.reddittop.presentation.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxWithPullToRefresh(
    modifier: Modifier,
    content: @Composable () -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
) {
    val state = rememberPullToRefreshState()
    PullToRefreshBox(
        modifier = modifier,
        state = state,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    ) {
        content()
    }
}

