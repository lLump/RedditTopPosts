package com.example.reddittop.presentation.viewmodel.model

import com.example.reddittop.domain.model.RedditTime

data class RequestInfo (
    var after: String? = null,
    var itemsLoaded: Int = 0,
    var time: RedditTime = RedditTime.ALL
)

fun RequestInfo.clearInfo() {
    after = null
    itemsLoaded = 0
}

fun RequestInfo.addNewInfo(newAfter: String?, count: Int) {
    after = newAfter
    itemsLoaded += count
}