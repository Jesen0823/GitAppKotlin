package com.jesen.cod.gitappkotlin.utils

import com.jesen.cod.common.sharedpreference.Preferences
import com.jesen.cod.gitappkotlin.AppContext
import kotlin.reflect.jvm.jvmName

inline fun <reified R, T> R.pref(name: String, default: T) = Preferences(
    AppContext, name, default
    , R::class.jvmName
)