package com.jesen.cod.gitappkotlin.setting

import com.jesen.cod.common.log.logger
import com.jesen.cod.gitappkotlin.AppContext
import com.jesen.cod.gitappkotlin.utils.deviceId

object Configs {
    object Account {
        val SCOPES = listOf("user", "repo", "notifications", "gist", "admin:org")
        // 在GitHub开发平台注册app拿到的ID
        // 地址：https://docs.github.com/en/developers/apps/creating-an-oauth-app
        const val clientId = "57d32dfb91357ac0f6e0"
        const val clientSecret = "aff7ce09b6d3e6f46cd99b07252a7f65cd401eb9"
        const val note = "kotliner.cn"
        const val noteUrl = "http://www.kotliner.cn"
        const val scope = "user"
        const val grant_type = "urn:ietf:params:oauth:grant-type:device_code"

        val fingerPrint by lazy {
            (AppContext.deviceId + clientId).also { logger.info("fingerPrint: $it") }
        }
    }
}