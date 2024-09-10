package com.example.reddittop.presentation.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.RadioButton
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
import com.example.reddittop.domain.model.Event
import com.example.reddittop.domain.model.MediaEvent
import com.example.reddittop.domain.model.RedditEvent
import com.example.reddittop.domain.model.RedditPost
import com.example.reddittop.domain.model.RedditTime
import com.example.reddittop.presentation.state.ScreenState
import com.example.reddittop.presentation.ui.screen.additional.BoxWithPullToRefresh
import com.example.reddittop.presentation.ui.screen.additional.ImageDialog
import com.example.reddittop.presentation.ui.screen.additional.ScrollListener
import com.example.reddittop.presentation.ui.screen.additional.ShowIsImageDownloaded

@Composable
fun MainScreen(
    onEvent: (Event) -> Unit,
    state: ScreenState,
    imageLoadingState: Boolean?,
    paddings: PaddingValues,
) {
    var isRefreshing by remember { mutableStateOf(false) }
    LaunchedEffect(state.isRefreshing) {
        isRefreshing = state.isRefreshing
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddings)
    ) {
        TimeSelectionRow { chosenTime ->
            onEvent(RedditEvent.ChangeTimeDiapason(chosenTime))
        }
        BoxWithPullToRefresh(
            modifier = Modifier.fillMaxSize(),
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
        ShowIsImageDownloaded(imageLoadingState)
    }
}

@Composable
private fun RedditPostsList(
    onEvent: (Event) -> Unit,
    state: ScreenState,
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
            PostItem(
                postDetails = post,
                openImage = { imageUrl ->
                    onEvent(MediaEvent.OpenImage(imageUrl))
                },
                downloadImage = { imageUrl ->
                    onEvent(MediaEvent.SaveImage(imageUrl))
                },
            )
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
private fun PostItem(
    postDetails: RedditPost,
    openImage: (String) -> Unit,
    downloadImage: (String) -> Unit,
) {
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
                imageUrl = postDetails.thumbnailUrl,
//                    ?: "https://styles.redditmedia.com/t5_2hk26o/styles/communityIcon_bc6r0xp98am41.png"
                // default image that I randomly picked up
                openImage = openImage,
                downloadImage = downloadImage,
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
private fun PostThumbnail(
    modifier: Modifier,
    imageUrl: String?,
    openImage: (String) -> Unit,
    downloadImage: (String) -> Unit,
) {
    if (imageUrl == "default") { // в реддите thumbnail never null (вроде бы как)
                                 // не проблема отрисовывать пустой image, проблема
                                 // в том, что его можно потом открыть
        Spacer(modifier = modifier)
    } else {
        var showDialogImage by remember { mutableStateOf(false) }
        Image(
            modifier = modifier.clickable { showDialogImage = true },
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        if (showDialogImage) {
            ImageDialog(
                imageUrl = imageUrl,
                isShowDialog = { showDialogImage = it },
                openImageUrl = openImage,
                downloadImage = downloadImage
            )
        }
    }
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

@Composable
private fun TimeSelectionRow(onTimeChosen: (RedditTime) -> Unit) {
    var selectedOption by remember { mutableStateOf(RedditTime.ALL) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Тут можно написать куда лаконичнее
        SingleTimeSelection(
            selectedOption = selectedOption,
            onClick = { chosenTime ->
                selectedOption = chosenTime
                onTimeChosen(chosenTime)
            },
            timeToChoose = RedditTime.ALL
        )
//        SingleTimeSelection(
//            selectedOption = selectedOption,
//            onClick = { chosenTime ->
//                selectedOption = chosenTime
//                onTimeChosen(chosenTime)
//            },
//            timeToChoose = RedditTime.YEAR
//        )
        SingleTimeSelection(
            selectedOption = selectedOption,
            onClick = { chosenTime ->
                selectedOption = chosenTime
                onTimeChosen(chosenTime)
            },
            timeToChoose = RedditTime.MONTH
        )
        SingleTimeSelection(
            selectedOption = selectedOption,
            onClick = { chosenTime ->
                selectedOption = chosenTime
                onTimeChosen(chosenTime)
            },
            timeToChoose = RedditTime.WEEK
        )
        SingleTimeSelection(
            selectedOption = selectedOption,
            onClick = { chosenTime ->
                selectedOption = chosenTime
                onTimeChosen(chosenTime)
            },
            timeToChoose = RedditTime.DAY
        )
        SingleTimeSelection(
            selectedOption = selectedOption,
            onClick = { chosenTime ->
                selectedOption = chosenTime
                onTimeChosen(chosenTime)
            },
            timeToChoose = RedditTime.HOUR
        )
    }
}

@Composable
private fun SingleTimeSelection(
    selectedOption: RedditTime,
    onClick: (RedditTime) -> Unit,
    timeToChoose: RedditTime,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onClick(timeToChoose)
        }
    ) {
        RadioButton(
            selected = (selectedOption == timeToChoose),
            onClick = { onClick(timeToChoose) }
        )
        Text(
            text = timeToChoose.invoke(),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}