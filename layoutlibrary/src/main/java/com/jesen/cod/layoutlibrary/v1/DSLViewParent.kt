package com.jesen.cod.layoutlibrary.v1

/**
 * 构造父类
 * */
import android.view.View
import android.view.ViewGroup

@Deprecated("V1版本存在隐式调用隐患，不可用")
interface DSLViewParent<out P : ViewGroup.MarginLayoutParams> {

    val <T : View> T.lparams: P
        get() = layoutParams as P
}