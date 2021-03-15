package com.jesen.cod.gitappkotlin.presenter

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.jesen.cod.common.ext.yes
import com.jesen.cod.gitappkotlin.BuildConfig
import com.jesen.cod.gitappkotlin.R
import com.jesen.cod.gitappkotlin.model.account.AccountManager
import com.jesen.cod.gitappkotlin.view.LoginActivity
import com.jesen.cod.mvp.impl.BasePresenter

const val TAG = "LoginPresenter"

class LoginPresenter : BasePresenter<LoginActivity>() {

    fun doLogin(name: String, passwd: String) {
        AccountManager.username = name
        AccountManager.passwd = passwd
        Log.i(TAG, "u:p=${name}:${passwd}")
        view.onLoginStart()
        AccountManager.getVerificationCode()
            .subscribe(
                {
                    Log.d(TAG, "getVerificationCode success")
                    view.onVerificationSuccess(it.verification_uri, it.user_code)

                    /*val result = GsonBuilder().create().toJson(it)
                    val jobject = JsonParser().parse(result).asJsonObject
                    Log.d(TAG, "next jobject :$jobject")*/
                },
                {
                    Log.d(TAG, "getVerificationCode fail")
                    view.onVerificationFailure(R.string.get_device_code_error.toString())
                    view.onLoginError(it)
                })
        /* AccountManager.login()
             .subscribe({
                 view.onLoginSuccess()
             },{
                 view.onLoginError(it)
             })*/
    }

    fun checkUserName(name: String): Boolean {
        return true
    }

    fun checkPasswd(passwd: String): Boolean {
        return true
    }

    override fun onResume() {
        super.onResume()
        if (BuildConfig.DEBUG) {
            view.onDataInit(BuildConfig.testUserName, BuildConfig.testPassword)
        } else {
            view.onDataInit(AccountManager.username, AccountManager.passwd)
        }
    }

    fun requestAccessToken() {
        AccountManager.postGetAccessToken().subscribe({
            view.onLoginSuccess()
        }, {
            view.onLoginError(it)
        })
    }
}