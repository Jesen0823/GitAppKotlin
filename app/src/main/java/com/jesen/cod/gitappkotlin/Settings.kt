package com.jesen.cod.gitappkotlin

import com.jesen.cod.common.Preferences


object Settings {
    var email: String by Preferences(AppContext, "email", "")
    var password: String by Preferences(AppContext, "password", "")

}

