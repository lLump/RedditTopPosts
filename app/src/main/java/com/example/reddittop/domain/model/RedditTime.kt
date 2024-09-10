package com.example.reddittop.domain.model

enum class RedditTime(private val time: String) {
    HOUR("hour"),
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    YEAR("year"), // empty answer
    ALL("all");

    operator fun invoke(): String = this.time
}