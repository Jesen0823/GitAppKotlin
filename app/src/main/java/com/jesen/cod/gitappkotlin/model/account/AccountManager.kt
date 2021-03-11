package com.jesen.cod.gitappkotlin.model.account

import android.accounts.AccountsException
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.jesen.cod.gitappkotlin.AppContext
import com.jesen.cod.gitappkotlin.entities.*
import com.jesen.cod.gitappkotlin.network.service.AuthService
import com.jesen.cod.gitappkotlin.network.service.UserService
import com.jesen.cod.gitappkotlin.setting.Configs
import com.jesen.cod.gitappkotlin.utils.fromJson
import com.jesen.cod.gitappkotlin.utils.pref
import org.jetbrains.anko.toast
import retrofit2.HttpException
import rx.Observable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

interface OnAccountStateChangeListener {

    fun onLogin(user: User)

    fun onLogout()
}

class AccountException(val authorizationRsp: AuthorizationRsp) : Exception("Already logged in.")

class VerificationCodeException(val verificationCodeRsp: VerificationCodeRsp) :
    Exception("Get verification Code fail")

const val TAG = "AccountManager"

private var map = mapOf("client_id" to Configs.Account.clientId, "scope" to Configs.Account.scope)


object AccountManager {
    var authId by pref(-1)
    var username by pref("")
    var passwd by pref("")
    var token by pref("")

    var device_code by pref("")
    var user_code by pref("")
    var verification_uri by pref("")

    // 保存用户信息
    private var userJson by pref("")

    var currentUser: User? = null
        get() {
            if (field == null && userJson.isNotEmpty()) {
                field = Gson().fromJson<User>(userJson) // util中封装的方法
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

    private fun notifyLogin(user: User) {
        onAccountStateChangeListeners.forEach {
            it.onLogin(user)
        }
    }

    private fun notifyLogout() {
        onAccountStateChangeListeners.forEach {
            it.onLogout()
        }
    }

    fun isLoggedIn(): Boolean = token.isNotEmpty()

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
                currentUser = it
                notifyLogin(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())


    fun login() =
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
                currentUser = it
                notifyLogin(it)
            }


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
        AuthService.getDeviceCode(VerificationCodeReq()/*Configs.Account.clientId,Configs.Account.scope*/)
            //AuthService.getDeviceCode(VerificationCodeReq(),map)
            .doOnNext {
                Log.i(TAG, "result:$it")
                if (it.device_code.isNotEmpty()) {
                    device_code = it.device_code
                    user_code = it.user_code
                    verification_uri = it.verification_uri
                    Log.i(
                        TAG, "verification result:\ndevice_code:${device_code}\nuser_code:" +
                                "${user_code}\nverification_uri:${verification_uri}"
                    )
                }
            }
            .doOnCompleted {
                Log.i(TAG, "doOnCompleted")
            }
            .doOnError {
                Log.i(TAG, "doOnError")

            }


    fun postDeviceAuthorization() =
        AuthService.deviceAuthorization(
            DeviceAuthorizationReq(
                Configs.Account.clientId,
                device_code,
                ""
            ),
            Configs.Account.clientId,
            device_code,
            ""
        ).doOnNext {

        }
}