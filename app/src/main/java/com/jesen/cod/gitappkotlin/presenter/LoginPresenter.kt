package com.jesen.cod.gitappkotlin.presenter

import com.jesen.cod.gitappkotlin.BuildConfig
import com.jesen.cod.gitappkotlin.model.account.AccountManager
import com.jesen.cod.gitappkotlin.view.LoginActivity
import com.jesen.cod.mvp.impl.BasePresenter

class LoginPresenter :BasePresenter<LoginActivity>(){

    fun doLogin(name:String, passwd:String){
        AccountManager.username = name
        AccountManager.passwd = passwd
        view.onLoginStart()
        AccountManager.login1()
            .subscribe({
                view.onLoginSuccess()
            },{
                view.onLoginError(it)
            })
    }

    fun checkUserName(name: String):Boolean{
        return TODO()
    }

    fun checkPasswd(passwd: String):Boolean{
        return TODO()
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