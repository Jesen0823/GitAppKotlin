package com.jesen.cod.gitappkotlin.model.account

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.jesen.cod.gitappkotlin.entities.*
import com.jesen.cod.gitappkotlin.network.service.AuthService
import com.jesen.cod.gitappkotlin.network.service.UserService
import com.jesen.cod.gitappkotlin.setting.Configs
import com.jesen.cod.gitappkotlin.utils.AppLog
import com.jesen.cod.gitappkotlin.utils.fromJson
import com.jesen.cod.gitappkotlin.utils.pref
import retrofit2.HttpException
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

interface OnAccountStateChangeListener {

    fun onLogin(user2: User2)

    fun onLogout()
}

@Deprecated("API旧协议,2020-11-13谷歌官方废弃")
class AccountException(val authorizationRsp: AuthorizationRsp) : Exception("Already logged in.")

class VerificationCodeException(val verificationCodeRsp: VerificationCodeRsp) :
    Exception("Get verification Code fail!")

class GetAccessTokenException(val deviceAuthorizationRsp: DeviceAuthorizationRsp) :
    Exception("Get Access_Token fail!")

private const val TAG = "AccountManager"

object AccountManager {
    var authId by pref("authId", -1)
    var username by pref("username", "")
    var passwd by pref("passwd", "")
    var token by pref("token", "")
    var token_type by pref("token_type", "")

    var device_code by pref("device_code", "")
    var user_code by pref("user_code", "")
    var verification_uri by pref("verification_uri", "")

    // 保存用户信息
    private var userJson by pref("userJson", "")

    private lateinit var currentVfcRsp: VerificationCodeRsp

    // var currentUser: User? = null...
    var currentUser: User2? = null
        get() {
            if (field == null && userJson.isNotEmpty()) {
                field = Gson().fromJson<User2>(userJson) // util中封装的方法
            }
            return field
        }
        set(value) {
            userJson = if (value == null) {
                ""
            } else {
                Gson().toJson(value)
            }
            field = value
        }

    val onAccountStateChangeListeners = ArrayList<OnAccountStateChangeListener>()

    private fun notifyLogin(user2: User2) {
        onAccountStateChangeListeners.forEach {
            it.onLogin(user2)
        }
    }

    private fun notifyLogout() {
        onAccountStateChangeListeners.forEach {
            it.onLogout()
        }
    }

    fun isLoggedIn(): Boolean = token.isNotEmpty()

    @Deprecated("API旧协议,2020-11-13谷歌官方废弃")
    fun login1() =
        AuthService.createAuthorization(AuthorizationReq())
            .doOnNext {
                if (it.token.isEmpty()) throw AccountException(it)
            }.retryWhen {
                it.flatMap {
                    if (it is AccountException) {
                        AuthService.deleteAuthorization(it.authorizationRsp.id)
                    } else {
                        Observable.error(it)
                    }
                }
            }
            .flatMap {
                token = it.token
                authId = it.id
                UserService.getAuthenticatedUser()
            }
            .map {
                //currentUser = it
                //notifyLogin(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())


    @Deprecated("API旧协议,2020-11-13谷歌官方废弃")
    fun login() =
        AuthService.createAuthorization(AuthorizationReq())
            .doOnNext {
                AppLog.i(TAG, "login doOnNext")
                if (it.token.isEmpty()) throw AccountException(it)
            }.retryWhen {
                AppLog.i(TAG, "login retryWhen")
                it.flatMap {
                    AppLog.i(TAG, "login retryWhen flatMap")
                    if (it is AccountException) {
                        AuthService.deleteAuthorization(it.authorizationRsp.id)
                    } else {
                        Observable.error(it)
                    }
                }
            }
            .flatMap {
                AppLog.i(TAG, "login flatMap")
                token = it.token
                authId = it.id
                UserService.getAuthenticatedUser()
            }
            .map {
                //currentUser = it
                //notifyLogin(it)
            }

    @Deprecated("API旧协议,2020-11-13谷歌官方废弃")
    fun logout1() =
        AuthService.deleteAuthorization(authId)
            .doOnNext {
                if (it.isSuccessful) {
                    authId = -1
                    token = ""
                    currentUser = null
                    notifyLogout()
                } else {
                    throw HttpException(it)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

    @Deprecated("API旧协议,2020-11-13谷歌官方废弃")
    fun logout() =
        AuthService.deleteAuthorization(authId)
            .doOnNext {
                if (it.isSuccessful) {
                    authId = -1
                    token = ""
                    currentUser = null
                    notifyLogout()
                } else {
                    throw HttpException(it)
                }
            }


    fun getVerificationCode() =
        AuthService.getDeviceCode(VerificationCodeReq())
            .doOnError {
                Log.d(
                    TAG,
                    "doOnError:${it.stackTrace}\ndoOnError message:${it.message}" +
                            "\ncause:${it.cause}"
                )
            }
            .doOnNext {
                device_code = it.device_code
                user_code = it.user_code
                verification_uri = it.verification_uri
                Log.i(
                    TAG, "verification result:\ndevice_code:${device_code}\nuser_code:" +
                            "${user_code}\nverification_uri:${verification_uri}"
                )
            }


    fun postGetAccessToken() =
        AuthService.deviceAuthorization(
            DeviceAuthorizationReq(
                Configs.Account.clientId,
                device_code,
                Configs.Account.grant_type
            )
        )
            .doOnError {
                Log.d(
                    TAG,
                    "doOnError:${it.stackTrace}\ndoOnError message:${it.message}" +
                            "\ncause:${it.cause}"
                )
            }
            .doOnNext {
                token = it.access_token
                //currentUser = it
                AppLog.d(TAG, "postGetAccessToken token:?$token")
            }
            .doOnNext {
                AppLog.d(TAG, "postGetAccessToken doOnNext, token:${it.access_token}")
                if (it.access_token.isEmpty()) throw GetAccessTokenException(it)
            }
            .flatMap {
                AppLog.d(TAG, "get access_token flatMap  and to getAuthenticatedUser.")
                token = it.access_token
                token_type = it.token_type
                UserService.getAuthenticatedUser2()
            }
            .map {
                AppLog.d(TAG, "getAuthenticatedUser2 map  and get currentUser.")
                currentUser = it
                notifyLogin(it)
            }

    fun copyToClipboard(content: String, context: Context) {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Label", content)
        clipboardManager.primaryClip = clipData
    }
}
