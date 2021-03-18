package com.jesen.cod.gitappkotlin.setting

import com.jesen.cod.common.sharedpreference.Preferences
import com.jesen.cod.gitappkotlin.AppContext
import com.jesen.cod.gitappkotlin.utils.pref


object Settings {
    var email: String by Preferences(
        AppContext,
        "email",
        ""
    )
    var password: String by Preferences(
        AppContext,
        "password",
        ""
    )

    var themeMode by pref("themeMode", "DAY")


}

