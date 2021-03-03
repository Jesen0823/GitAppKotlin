package com.jesen.cod.mvp
/**
 * 生命周期函数
 *
 * **/
import android.content.res.Configuration
import android.os.Bundle

interface ILifecycle {

    fun onCreate(savedInstanceState: Bundle?)

    fun onSaveInstanceState(outState: Bundle)

    fun onViewStateRestored(savedInstanceState: Bundle?)

    fun onConfigurationChanged(newConfig: Configuration)

    fun onDestroy()

    fun onStart()

    fun onStop()

    fun onResume()

    fun onPause()
}