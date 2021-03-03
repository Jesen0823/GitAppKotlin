package com.jesen.cod.gitappkotlin

import android.app.Application
import android.content.ContextWrapper

private lateinit var INSTANCE:Application

class GitApp: Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

}

object AppContext: ContextWrapper(INSTANCE)
