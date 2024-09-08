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
import com.example.reddittop.domain.model.RedditEvent
import com.example.reddittop.presentation.ui.screen.MainScreen
import com.example.reddittop.presentation.ui.theme.RedditTopTheme
import com.example.reddittop.presentation.viewmodel.RedditViewModel

class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: RedditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val container = (application as MyApplication).apiContainer
        mainViewModel = RedditViewModel(remoteRepo = container.redditRepo)
        mainViewModel.onEvent(RedditEvent.RefreshScreen)

        enableEdgeToEdge()
        setContent {
            RedditTopTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val postsState by mainViewModel.state.collectAsState()
                    MainScreen(
                        onEvent = mainViewModel::onEvent,
                        state = postsState,
                        paddings = innerPadding
                    )
                }
            }
        }
    }
}