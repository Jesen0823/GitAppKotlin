package com.jesen.cod.gitappkotlin.utils

import rx.Subscription
import rx.Observable
import java.util.*

fun <T> Observable<T>.subscribeIgnoreError(onNext: (T) -> Unit): Subscription =
    subscribe(onNext, Throwable::printStackTrace)