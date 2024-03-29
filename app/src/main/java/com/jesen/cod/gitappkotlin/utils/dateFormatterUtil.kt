package com.jesen.cod.gitappkotlin.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.TimeZone.*

val dateFormatter by lazy {
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CHINA)
        .apply {
            timeZone = getTimeZone("GMT")
        }
}

val outputDateFormatter by lazy {
    SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
}

fun Date.format(pattern: String): String = SimpleDateFormat(pattern, Locale.CHINA).format(this)

fun timeToDate(time: String) = dateFormatter.parse(time)

fun Date.view(): String {
    val now = System.currentTimeMillis()
    val seconds = (now - this.time) / 1000
    val minutes = seconds / 60
    return if (minutes >= 60) {
        val hours = minutes / 60
        if (hours in 24..47) {
            "Yesterday"
        } else if (hours >= 48) {
            outputDateFormatter.format(this)
        } else {
            "$hours hours ago"
        }
    } else if (minutes < 1) {
        "$seconds seconds ago"
    } else {
        "$minutes minutes ago"
    }
}