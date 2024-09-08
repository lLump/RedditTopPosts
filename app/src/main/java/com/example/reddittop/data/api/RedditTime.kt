package com.example.reddittop.data.api

enum class RedditTime(private val time: String) {
    HOUR("hour"),
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
//    YEAR("year"), //internal error
    ALL("all");

    operator fun invoke(): String = this.time
}