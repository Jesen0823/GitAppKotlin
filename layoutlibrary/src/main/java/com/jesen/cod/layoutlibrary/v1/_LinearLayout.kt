package com.jesen.cod.layoutlibrary.v1

import android.content.Context
import android.view.View
import android.widget.LinearLayout

const val MATCH_PARENT = -1
const val WRAP_CONTENT = -2

@Deprecated("V1版本存在隐式调用隐患，不可用")
class _LinearLayout(context: Context) : LinearLayout(context),
    DSLViewParent<LinearLayout.LayoutParams> {

    /*val <T:View> T.lparams:LinearLayout.LayoutParams
    get() = this.layoutParams as LayoutParams*/


    var <T : View>T.layoutWeight: Float
        set(value) {
            lparams.weight = value
        }
        get() {
            return lparams.weight
        }

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