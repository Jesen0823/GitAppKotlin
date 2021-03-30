package com.jesen.cod.gitappkotlin.setting

import com.jesen.cod.gitappkotlin.AppContext
import com.jesen.cod.gitappkotlin.R
import com.jesen.cod.gitappkotlin.model.account.AccountManager
import com.jesen.cod.gitappkotlin.utils.pref


object Settings {
    private var defaultPageForUser by pref("defaultPageForUser", R.id.navRepos)
    private var defaultPageForVisitor by pref("defaultPageForVisitor", R.id.navRepos)
    private var lastPageIdString by pref("lastPageIdString", "")
    var themeMode by pref("themeMode", "DAY")

    var lastPage: Int
        get() = if (lastPageIdString.isEmpty()) 0 else AppContext.resources.getIdentifier(
            lastPageIdString,
            "id", AppContext.packageName
        )
        set(value) {
            lastPageIdString = AppContext.resources.getResourceEntryName(value)
        }

    val defaultPage
        get() = if (AccountManager.isLoggedIn()) defaultPageForUser else defaultPageForVisitor


    /*var email: String by Preferences(
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
*/

}

