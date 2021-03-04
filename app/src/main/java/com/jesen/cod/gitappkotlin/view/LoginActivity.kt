package com.jesen.cod.gitappkotlin.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.jesen.cod.gitappkotlin.R
import com.jesen.cod.gitappkotlin.presenter.LoginPresenter
import com.jesen.cod.gitappkotlin.ui.login.LoginViewModel
import com.jesen.cod.mvp.impl.BaseActivity

class LoginActivity : BaseActivity<LoginPresenter>() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

    }


}
