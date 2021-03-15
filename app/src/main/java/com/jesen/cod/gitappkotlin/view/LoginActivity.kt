package com.jesen.cod.gitappkotlin.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.jesen.cod.common.ext.otherwise
import com.jesen.cod.common.ext.yes
import com.jesen.cod.common.log.logger

import com.jesen.cod.gitappkotlin.R
import com.jesen.cod.gitappkotlin.model.account.AccountManager
import com.jesen.cod.gitappkotlin.presenter.LoginPresenter
import com.jesen.cod.gitappkotlin.utils.*
import com.jesen.cod.mvp.impl.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

private const val TAG = "LoginActivity"

class LoginActivity : BaseActivity<LoginPresenter>(), AlertDialogListener {

    // findViewById不要也行，因为有KAE即 apply plugin: 'kotlin-android-extensions'
    private val signInButton by lazy { findViewById<Button>(R.id.signInButton) }
    private val userName by lazy { findViewById<EditText>(R.id.userName) }
    private val password by lazy { findViewById<EditText>(R.id.password) }
    private val loginProgress by lazy { findViewById<ProgressBar>(R.id.loginProgress) }
    //private lateinit var dialogUtil: DialogUtil
    private lateinit var dialogUtil: DialogU

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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
                            showTips(password, resources.getString(R.string.passwd_invialid))
                        }
                }
                .otherwise {
                    showTips(userName, resources.getString(R.string.username_invialid))
                }
        }

    }

    private fun showTips(view: EditText, message: String) {
        view.requestFocus()
        view.error = message
    }

    fun onDataInit(name: String, passwd: String) {
        userName.setText(name)
        password.setText(passwd)
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

    fun onCancelLogin() {
        showProgress(false)
        toast("取消登录")
    }

    /**
     * step 1,获取device_code&verification_uri成功
     * */
    fun onVerificationSuccess(verificationUri: String, userCode: String) {
        Log.d(TAG, "onVerificationSuccess")
        val obj: AlertElement = AlertElement(
            resources.getString(R.string.alert_title),
            resources.getString(R.string.alert_message).format(userCode),
            resources.getString(R.string.alert_positive),
            resources.getString(R.string.alert_negative),
            resources.getString(R.string.alert_neutral),
            verificationUri,
            userCode
        )
        // 方案一：弹窗里面嵌入webView，web无法输入，放弃
        /*dialogUtil = DialogUtil(this)
        dialogUtil.showAlertDialog(this, obj)*/
        // 方案二：弹窗后拉起浏览器
        dialogUtil = DialogU(this)
        dialogUtil.showAlertDialog(this, obj)
    }

    /**
     * step 1,获取device_code&verification_uri失败
     * */
    fun onVerificationFailure(tips: String) {
        toast(tips)
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

    /**
     * 打开已安装浏览器
     * */
    fun openWebPage(url: String) {
        Log.d(TAG, "openWebPage$url")
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun sendResult(type: ClickType) {
        when (type) {
            ClickType.POSITIVE -> {
                Log.d(TAG, "sendResult POSITIVE")
                presenter.requestAccessToken()
            }
            ClickType.NEGATIVE -> {
                Log.d(TAG, "sendResult NEGATIVE")
                onCancelLogin()
            }
            ClickType.NEUTRAL -> {
                Log.d(TAG, "sendResult NEUTRAL")
                Log.d(TAG, "verification_uri NEUTRAL${AccountManager.verification_uri}")
                openWebPage(AccountManager.verification_uri)
                AccountManager.copyToClipboard(AccountManager.user_code.trim(), this)

            }
        }
    }
}
