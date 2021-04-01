package com.jesen.cod.gitappkotlin

import android.app.Application
import android.content.ContextWrapper
import com.bennyhuo.swipefinishable.SwipeFinishable
import com.bennyhuo.tieguanyin.runtime.core.ActivityBuilder
import com.jesen.cod.common.log.logger

private lateinit var INSTANCE:Application

class GitApp: Application() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        ActivityBuilder.INSTANCE.init(this)
        SwipeFinishable.INSTANCE.init(this)

        logger.isInfoEnabled
    }

}

object AppContext: ContextWrapper(INSTANCE)
