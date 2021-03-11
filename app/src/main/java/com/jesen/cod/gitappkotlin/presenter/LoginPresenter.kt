package com.jesen.cod.gitappkotlin.presenter

import android.util.Log
import com.jesen.cod.gitappkotlin.BuildConfig
import com.jesen.cod.gitappkotlin.model.account.AccountManager
import com.jesen.cod.gitappkotlin.view.LoginActivity
import com.jesen.cod.mvp.impl.BasePresenter

const val TAG = "LoginPresenter"
class LoginPresenter :BasePresenter<LoginActivity>(){

    fun doLogin(name:String, passwd:String){
        AccountManager.username = name
        AccountManager.passwd = passwd
        Log.i(TAG, "u:p=${name}:${passwd}")
        view.onLoginStart()
        AccountManager.getVerificationCode()
        Log.i(TAG, "getVerificationCode end.")

        /*AccountManager.login()
            .subscribe({
                view.onLoginSuccess()
            },{
                view.onLoginError(it)
            })*/
    }

    fun checkUserName(name: String):Boolean{
        return true
    }

    fun checkPasswd(passwd: String):Boolean{
        return true
    }

    override fun onResume() {
        super.onResume()
        if (BuildConfig.DEBUG){
            view.onDataInit(BuildConfig.testUserName,BuildConfig.testPassword)
        }else {
            view.onDataInit(AccountManager.username, AccountManager.passwd)
        }
    }
}