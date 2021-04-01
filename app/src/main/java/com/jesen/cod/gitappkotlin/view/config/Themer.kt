package com.jesen.cod.gitappkotlin.view.config

import android.app.Activity
import android.support.annotation.StyleRes
import com.githang.statusbar.StatusBarCompat
import com.jesen.cod.gitappkotlin.R
import com.jesen.cod.gitappkotlin.setting.Settings

object Themer {
    enum class ThemeMode(
        @StyleRes val normal: Int, @StyleRes val translucent: Int
    ) {
        DAY(R.style.AppTheme, R.style.AppTheme_Translucent),
        NIGHT(R.style.AppTheme_Dark, R.style.AppTheme_Dark_Translucent)
    }

    fun currentTheme() = ThemeMode.valueOf(Settings.themeMode)

    /*选择主题*/
    fun applyProperTheme(activity: Activity, translucent: Boolean = false) {
        activity.setTheme(currentTheme().let {
            if (translucent) it.translucent else it.normal
        })
        var originalColor = R.color.colorPrimary
        if (currentTheme().equals(ThemeMode.NIGHT)) {
            originalColor = R.color.colorPrimaryInverse
        }
        StatusBarCompat.setStatusBarColor(
            activity, activity.resources.getColor(originalColor),
            false
        );
    }

    fun toggle(activity: Activity) {
        when (currentTheme()) {
            Themer.ThemeMode.DAY -> Settings.themeMode = ThemeMode.NIGHT.name
            Themer.ThemeMode.NIGHT -> Settings.themeMode = ThemeMode.DAY.name
        }
        activity.recreate()
    }

}