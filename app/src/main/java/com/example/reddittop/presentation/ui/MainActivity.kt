package com.example.reddittop.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.reddittop.di.MyApplication
import com.example.reddittop.domain.model.RoomEvent
import com.example.reddittop.presentation.ui.screen.MainScreen
import com.example.reddittop.presentation.ui.theme.RedditTopTheme
import com.example.reddittop.presentation.viewmodel.RedditViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel by lazy {
        val app = (application as MyApplication)
        RedditViewModel(
            remoteRepo = app.apiContainer.redditRepo,
            localRepo = app.roomContainer.roomRepo,
            openImageByUrl = app.imageContainer.urlOpener,
            downloadImage = app.imageContainer.imageDownloader
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            RedditTopTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val postsState by mainViewModel.state.collectAsState()
                    val imageLoadingState by mainViewModel.isImageDownloaded.collectAsState()
                    MainScreen(
                        onEvent = mainViewModel::onEvent,
                        state = postsState,
                        imageLoadingState = imageLoadingState,
                        paddings = innerPadding,
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.onEvent(RoomEvent.RestoreInfo)
        // есть баг, при быстром повороте экрана и относительно большом кол-ве данных (локально сейвиться пачка целиком),
        // данные не успевают перезаписаться из-за чего UI получает пустой список и загружает изначальную пачку
        // из интернета.
        // В теории можно исправить объеденив восстановление и сохранение в одном ивенте, запуская все в одной корутине,
        // а не паралельно как сейчас. Но этот метод мне был не по нраву, а иначе решить к сожалению не успел.
    }

    override fun onStop() {
        super.onStop()
        mainViewModel.onEvent(RoomEvent.SaveInfo)
    }
}