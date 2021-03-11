package com.jesen.cod.gitappkotlin.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.jesen.cod.common.ext.otherwise
import com.jesen.cod.common.ext.yes
import com.jesen.cod.common.log.logger

import com.jesen.cod.gitappkotlin.R
import com.jesen.cod.gitappkotlin.presenter.LoginPresenter
import com.jesen.cod.gitappkotlin.ui.login.LoginViewModel
import com.jesen.cod.gitappkotlin.utils.NetUtil
import com.jesen.cod.gitappkotlin.utils.hideSoftInput
import com.jesen.cod.mvp.impl.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

const val TAG = "LoginActivity"

class LoginActivity : BaseActivity<LoginPresenter>() {

    // findViewById不要也行，因为有KAE即 apply plugin: 'kotlin-android-extensions'
    private val signInButton by lazy { findViewById<Button>(R.id.signInButton) }
    private val userName by lazy { findViewById<EditText>(R.id.userName) }
    private val password by lazy { findViewById<EditText>(R.id.password) }
    private val loginProgress by lazy { findViewById<ProgressBar>(R.id.loginProgress) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var s = NetUtil.getMobileIp()
        logger.debug(TAG, s)
        Log.d(TAG, s)

        signInButton.setOnClickListener {
            Log.i(TAG, "click login")
            toast("click me")
            presenter.checkUserName(userName.text.toString())
                .yes {
                    presenter.checkPasswd(password.text.toString())
                        .yes {
                            hideSoftInput()
                            Log.i(TAG, "doLogin")
                            presenter.doLogin(userName.text.toString(), password.text.toString())
                        }
                        .otherwise {
                            showTips(password, R.string.passwd_invialid.toString())
                        }
                }
                .otherwise {
                    showTips(userName, R.string.username_invialid.toString())
                }
        }

    }

    private fun showTips(view: EditText, message: String) {
        view.requestFocus()
        view.error = message
    }

    fun onLoginStart() {
        showProgress(true)
    }

    fun onLoginError(e: Throwable) {
        e.printStackTrace()
        showProgress(false)
        toast("登录失败")
    }

    fun onLoginSuccess() {
        showProgress(false)
        toast("登录成功")
    }

    fun onDataInit(name: String, passwd: String) {
        userName.setText(name)
        password.setText(passwd)
    }

    private fun showProgress(show: Boolean) {
        val showAnimTime = resources.getInteger(android.R.integer.config_longAnimTime)

        /*loginForm.animate().setDuration(shortAnimTime.toLong()).alpha(
            (if (show) 0 else 1).toFloat()).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                loginForm.visibility = if (show) View.GONE else View.VISIBLE
            }
        })*/

        loginProgress.animate().setDuration(showAnimTime.toLong())
            .alpha((if (show) 1 else 0).toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    loginProgress.visibility = if (show) View.VISIBLE else View.INVISIBLE
                }
            })
    }

}
