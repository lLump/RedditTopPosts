package com.example.reddittop.presentation.ui.screen.additional

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter

@Composable
fun ImageDialog(
    imageUrl: String?,
    isShowDialog: (Boolean) -> Unit,
    openImageUrl: (String) -> Unit,
    downloadImage: (String) -> Unit,
) {
    Dialog(onDismissRequest = { isShowDialog(false) }) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .heightIn(min = 350.dp, max = 500.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    downloadImage(imageUrl ?: "IMPOSSIBLE")
                }) {
                    Text("Download")
                }

                Button(onClick = {
                    openImageUrl(imageUrl ?: "IMPOSSIBLE")
                }) {
                    Text("Open Full")
                }
            }
        }
    }
}

@Composable
fun ShowIsImageDownloaded(imageLoadingState: Boolean?) {
    val context = LocalContext.current
    LaunchedEffect(imageLoadingState) {
        when (imageLoadingState) {
            true -> Toast.makeText(context, "Image downloaded on your device", Toast.LENGTH_SHORT).show()
            false -> Toast.makeText(context, "Something happened, never try again", Toast.LENGTH_SHORT).show()
            else -> {}
        }
    }
}