package com.jesen.cod.common

sealed class BooleanExt<out T>

object Otherwise : BooleanExt<Nothing>()
class WithData<T>(val data:T) : BooleanExt<T>()

inline fun <T>Boolean.yes(block: () -> T) =
    when {
        this -> {
            WithData(block())
        }
        else -> {
            Otherwise
        }

    }

fun <T>BooleanExt<T>.otherwise(block: () -> T):T =
    when(this){
        is Otherwise -> block()
        is WithData<T> -> this.data
    }