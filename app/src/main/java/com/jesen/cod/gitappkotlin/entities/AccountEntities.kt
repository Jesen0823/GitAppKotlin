package com.jesen.cod.gitappkotlin.entities

import com.jesen.cod.common.anno.PoKo
import com.jesen.cod.gitappkotlin.setting.Configs

@Deprecated("API旧协议,2020-11-13谷歌官方废弃")
@PoKo
data class AuthorizationReq(
    var scopes: List<String> = Configs.Account.SCOPES,
    var note: String = Configs.Account.note,
    var note_url: String = Configs.Account.noteUrl,
    var client_secret: String = Configs.Account.clientSecret
)

@Deprecated("API旧协议,2020-11-13谷歌官方废弃")
@PoKo
data class AuthorizationRsp(
    var id: Int,
    var url: String,
    var app: App,
    var token: String,
    var hashed_token: String,
    var token_last_eight: String,
    var note: String,
    var note_url: String,
    var created_at: String,
    var updated_at: String,
    var scopes: List<String>
)

@PoKo
data class App(var name: String, var url: String, var client_id: String)

@PoKo
data class VerificationCodeReq(
    var client_id: String = Configs.Account.clientId,
    var scope: String = Configs.Account.scope
)

@PoKo
data class VerificationCodeRsp(
    var device_code: String,
    var user_code: String,
    var verification_uri: String,
    var expires_in: Int,
    var interval: Int
)

@PoKo
data class DeviceAuthorizationReq(
    var client_id: String = Configs.Account.clientId,
    var device_code: String,
    var grant_type: String
)

@PoKo
data class DeviceAuthorizationRsp(
    var access_token: String,
    var token_type: String,
    var scope: String
)