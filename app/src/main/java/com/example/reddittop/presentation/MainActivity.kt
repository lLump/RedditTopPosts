package com.example.reddittop.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reddittop.di.MyApplication
import com.example.reddittop.presentation.ui.theme.RedditTopTheme
import com.example.reddittop.presentation.viewmodel.RedditViewModel
import com.example.reddittop.presentation.viewmodel.model.RedditEvent

class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: RedditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val container = (application as MyApplication).apiContainer
        mainViewModel = RedditViewModel(remoteRepo = container.redditRepo)
        enableEdgeToEdge()
        setContent {
            RedditTopTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TestApi(mainViewModel::onEvent, innerPadding)
                }
            }
        }
    }
}

@Composable
fun TestApi(event: (RedditEvent) -> Unit, innerPadding: PaddingValues) {
    Button(onClick = { event(RedditEvent.RefreshScreen) }) {
        Text(
            text = "Click me",
            modifier = Modifier
                .padding(innerPadding)
                .size(35.dp)
        )
    }
}