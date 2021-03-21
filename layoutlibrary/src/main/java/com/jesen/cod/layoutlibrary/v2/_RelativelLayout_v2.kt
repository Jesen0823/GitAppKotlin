package com.jesen.cod.layoutlibrary.v1

import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.jesen.cod.layoutlibrary.v2.DSLViewParent_v2
import com.jesen.cod.layoutlibrary.v2.WRAP_CONTENT_v2

class _RelativelLayout_v2(context: Context) : RelativeLayout(context), DSLViewParent_v2 {

    fun <T : View> T.lparams(
        width: Int = WRAP_CONTENT_v2, height: Int = WRAP_CONTENT_v2,
        init: LayoutParams.() -> Unit
    ) {
        this.layoutParams = LayoutParams(width, height).also(init)
    }

}