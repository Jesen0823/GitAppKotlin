package com.jesen.cod.gitappkotlin.utils

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.jesen.cod.gitappkotlin.R

private const val TAG = "DialogUtil"

/**
 * AlertDialog数据实体
 * */
data class AlertElement(
    var title: String,
    var message: String,
    var positive: String,
    var negative: String,
    var neutral: String?,
    var vertifUrl: String,
    var userCode: String
)

enum class ClickType {
    POSITIVE, NEGATIVE, NEUTRAL
}

interface AlertDialogListener {
    fun sendResult(type: ClickType)
}

/**
 * AlertDialog简单弹窗，加入了WebView
 * */
class DialogUtil(clickListener: AlertDialogListener) {
    val listener = clickListener
    lateinit var dialog: AlertDialog

    fun showAlertDialog(context: Context?, element: AlertElement) {

        val webView = WebView(context)
        webView.apply {
            loadUrl(element.vertifUrl)
            isInEditMode
            isFocusableInTouchMode
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                focusable
            }
            canGoBackOrForward(3)
            isClickable = true
            settings.javaScriptEnabled
            requestFocus()
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    url: String
                ): Boolean {
                    view.isInEditMode
                    view.isFocusableInTouchMode
                    view.loadUrl(url)
                    view.requestFocus()
                    return true
                }
            }
            requestFocus(View.FOCUS_DOWN)
            setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP -> v.requestFocusFromTouch()
                    MotionEvent.ACTION_MOVE -> {
                    }
                    MotionEvent.ACTION_CANCEL -> {
                    }
                }
                false
            }
            requestFocus()
        }

        dialog =
            AlertDialog.Builder(context!!)
                .setTitle(element.title)
                .setMessage(element.message)
                .setIcon(R.drawable.sign)
                .setView(webView)
                .setCancelable(false)
                .setPositiveButton(element.positive) { dialog, which ->
                    listener.sendResult(ClickType.POSITIVE)
                    Toast.makeText(context, element.positive, Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(element.negative) { _, _ ->
                    listener.sendResult(ClickType.NEGATIVE)
                    Toast.makeText(context, element.negative, Toast.LENGTH_SHORT).show()
                }
                .setNeutralButton(element.neutral) { _, _ ->
                    AppLog.d(TAG, "BUTTON_NEGATIVE setNeutralButton ")
                }
                .create()
        webView.requestFocus()
        dialog.show()
        // 为了防止按钮触发弹窗消失，重写按钮点击监听
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener {
            AppLog.d(TAG, "BUTTON_NEGATIVE setOnClickListener ")
            listener.sendResult(ClickType.NEUTRAL)
            Toast.makeText(context, element.neutral, Toast.LENGTH_SHORT).show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                listener.sendResult(ClickType.POSITIVE)
            }
        }
    }
}

/**
 * AlertDialog简单弹窗
 * */
class DialogU(clickListener: AlertDialogListener) {
    val listener = clickListener
    lateinit var dialog: AlertDialog

    fun showAlertDialog(context: Context?, element: AlertElement) {

        dialog =
            AlertDialog.Builder(context!!)
                .setTitle(element.title)
                .setMessage(element.message)
                .setIcon(R.drawable.sign)
                .setCancelable(false)
                .setPositiveButton(element.positive) { dialog, which ->
                    listener.sendResult(ClickType.POSITIVE)
                    Toast.makeText(context, element.positive, Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(element.negative) { _, _ ->
                    listener.sendResult(ClickType.NEGATIVE)
                    Toast.makeText(context, element.negative, Toast.LENGTH_SHORT).show()
                }
                .setNeutralButton(element.neutral) { _, _ ->
                    AppLog.d(TAG, "BUTTON_NEGATIVE setNeutralButton ")
                }
                .setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        dialog?.dismiss()
                        return@OnKeyListener true
                    }
                    false
                })
                .create()
        dialog.show()
        // 为了防止按钮触发弹窗消失，重写按钮点击监听
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener {
            AppLog.d(TAG, "BUTTON_NEGATIVE setOnClickListener ")
            listener.sendResult(ClickType.NEUTRAL)
            Toast.makeText(context, element.neutral, Toast.LENGTH_SHORT).show()
        }
    }
}
