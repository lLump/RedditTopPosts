package com.example.reddittop.presentation.viewmodel.model

data class RequestInfo (
    var after: String? = null,
    var itemsLoaded: Int = 0
)

fun RequestInfo.clearInfo() {
    after = null
    itemsLoaded = 0
}

fun RequestInfo.addNewInfo(newAfter: String, count: Int) {
    after = newAfter
    itemsLoaded += count
}