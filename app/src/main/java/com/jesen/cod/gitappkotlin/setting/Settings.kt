package com.jesen.cod.gitappkotlin.setting

import com.jesen.cod.common.sharedpreference.Preferences
import com.jesen.cod.gitappkotlin.AppContext


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

}

