package com.jesen.cod.gitappkotlin

import android.app.Application
import android.content.ContextWrapper
import com.jesen.cod.common.log.logger

private lateinit var INSTANCE:Application

class GitApp: Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        logger.isInfoEnabled
    }

}

object AppContext: ContextWrapper(INSTANCE)
