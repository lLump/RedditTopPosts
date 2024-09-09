package com.example.reddittop.presentation.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

@Composable
fun ScrollListener(
    listState: LazyListState,
    loadNext: () -> Unit,
) {
    val reachedBottom: Boolean by remember {
        derivedStateOf { listState.reachedBottom(buffer = 1) }
    }
    LaunchedEffect(reachedBottom) {
        if (reachedBottom) loadNext()
    }
}

private fun LazyListState.reachedBottom(buffer: Int): Boolean {
    val lastVisibleItem = this.layoutInfo.visibleItemsInfo.lastOrNull()
    return lastVisibleItem?.index != 0 && lastVisibleItem?.index == this.layoutInfo.totalItemsCount - buffer
}