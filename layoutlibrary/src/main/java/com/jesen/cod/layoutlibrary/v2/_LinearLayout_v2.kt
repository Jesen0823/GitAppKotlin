package com.jesen.cod.layoutlibrary.v2

import android.content.Context
import android.view.View
import android.widget.LinearLayout


class _LinearLayout_v2(context: Context) : LinearLayout(context), DSLViewParent_v2 {

    fun <T : View> T.lparams(
        width: Int = WRAP_CONTENT_v2, height: Int = WRAP_CONTENT_v2,
        init: LayoutParams.() -> Unit
    ) {
        this.layoutParams = LayoutParams(width, height).also(init)
    }

}