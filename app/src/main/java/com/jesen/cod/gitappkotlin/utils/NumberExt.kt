package com.jesen.cod.gitappkotlin.utils

fun Int.toKilo(): String {
    return if (this > 700) {
        "${(Math.round(this / 100f) / 10f)}k"
    } else {
        "$this"
    }
}