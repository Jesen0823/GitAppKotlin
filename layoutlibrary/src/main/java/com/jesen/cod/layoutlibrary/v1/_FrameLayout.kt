package com.jesen.cod.layoutlibrary.v1

/**
 * V1版本存在隐式调用隐患，不可用
 * */
import android.content.Context
import android.view.View
import android.widget.FrameLayout

@Deprecated("V1版本存在隐式调用隐患，不可用")
class _FrameLayout(context: Context) : FrameLayout(context),
    DSLViewParent<FrameLayout.LayoutParams> {

    var <T : View>T.layoutGravity: Int
        set(value) {
            lparams.gravity = value
        }
        get() {
            return lparams.gravity
        }

    var <T : View>T.layoutWidth: Int
        set(value) {
            lparams.width = value
        }
        get() {
            return lparams.width
        }

    var <T : View>T.layoutHeight: Int
        set(value) {
            lparams.height = value
        }
        get() {
            return lparams.height
        }
}