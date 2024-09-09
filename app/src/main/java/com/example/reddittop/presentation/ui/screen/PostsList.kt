package com.example.reddittop.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.reddittop.domain.model.RedditEvent
import com.example.reddittop.domain.model.RedditPost
import com.example.reddittop.presentation.state.PostsState

@Composable
fun MainScreen(
    onEvent: (RedditEvent) -> Unit,
    state: PostsState,
    paddings: PaddingValues,
) {
    var isRefreshing by remember { mutableStateOf(false) }
    LaunchedEffect(state.isRefreshing) {
        isRefreshing = state.isRefreshing
    }
    BoxWithPullToRefresh(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddings),
        content = {
            RedditPostsList(
                onEvent = onEvent,
                state = state,
            )
        },
        onRefresh = {
            isRefreshing = true
            onEvent(RedditEvent.RefreshScreen)
        },
        isRefreshing = isRefreshing
    )
}

@Composable
private fun RedditPostsList(
    onEvent: (RedditEvent) -> Unit,
    state: PostsState,
) {
    val listState = rememberLazyListState()
    ScrollListener(
        listState = listState,
        loadNext = { onEvent(RedditEvent.LoadNextPosts) }
    )

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(state.posts) { post ->
            PostItem(postDetails = post)
        }

        if (state.isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun PostItem(postDetails: RedditPost) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            PostThumbnail(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterVertically)
                    .clip(CircleShape),
                imageUrl = postDetails.thumbnailUrl
//                    ?: "https://styles.redditmedia.com/t5_2hk26o/styles/communityIcon_bc6r0xp98am41.png"
                // default image that I randomly picked up
            )
            // theme, author, hours ago
            PostContent(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                post = postDetails
            )

            PostComments(
                modifier = Modifier.align(Alignment.Bottom),
                commentsAmount = postDetails.commentsAmount
            )
        }
    }
}

@Composable
private fun PostThumbnail(modifier: Modifier, imageUrl: String?) {
    Image(
        modifier = modifier,
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun PostContent(modifier: Modifier, post: RedditPost) {
    Column(modifier = modifier) {
        Text(
            text = post.theme,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Posted by ${post.author}", fontSize = 12.sp)
        Text(
            text = post.timeAgo,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun PostComments(modifier: Modifier, commentsAmount: Int) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
    ) {
        Text(text = "$commentsAmount comments", fontSize = 12.sp)
    }
}