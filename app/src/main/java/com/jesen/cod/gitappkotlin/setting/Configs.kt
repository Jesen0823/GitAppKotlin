package com.jesen.cod.gitappkotlin.setting

import com.jesen.cod.common.log.logger
import com.jesen.cod.gitappkotlin.AppContext
import com.jesen.cod.gitappkotlin.utils.deviceId

object Configs {
    object Account {
        val SCOPES = listOf("user", "repo", "notifications", "gist", "admin:org")
        val TOKEN = "fdebd8ad0c1513f815498b3b1e0b9d2fdb2903ca"
        // Client ID: 57d32dfb91357ac0f6e0
        const val clientId = "57d32dfb91357ac0f6e0"
        // Client secrets: aff7ce09b6d3e6f46cd99b07252a7f65cd401eb9
        const val clientSecret = "aff7ce09b6d3e6f46cd99b07252a7f65cd401eb9"
        const val note = "kotliner.cn"
        const val noteUrl = "http://www.kotliner.cn"
        const val scope = "user"

        val fingerPrint by lazy {
            (AppContext.deviceId + clientId).also { logger.info("fingerPrint: $it") }
        }
    }
}