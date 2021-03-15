package com.jesen.cod.gitappkotlin.utils

import android.util.Log
import com.jesen.cod.gitappkotlin.AppContext
import com.jesen.cod.gitappkotlin.GitApp

object AppLog {
    private const val VERBOSE = 1
    private const val DEBUG = 2
    private const val INFO = 3
    private const val WARN = 4
    private const val ERROR = 5
    private val TAG_HEADER = AppUtil.getAppName(AppContext)

    private var level = VERBOSE

    fun decTag(tag: String): String = "||$TAG_HEADER|| $tag"

    fun v(tag: String, msg: String) {
        if (level <= VERBOSE) {
            Log.v(decTag(tag), msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (level <= DEBUG) {
            Log.d(decTag(tag), msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (level <= INFO) {
            Log.i(decTag(tag), msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (level <= WARN) {
            Log.w(decTag(tag), msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (level <= ERROR) {
            Log.e(decTag(tag), msg)
        }
    }

}