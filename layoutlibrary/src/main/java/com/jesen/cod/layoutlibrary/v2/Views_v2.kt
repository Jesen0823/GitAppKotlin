package com.jesen.cod.layoutlibrary.v2

import android.app.Activity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout

import com.jesen.cod.layoutlibrary.v1._RelativelLayout_v2

//region ViewGroup
inline fun <reified T : ViewGroup> T.relativeLayout(init: (@DslViewMarker _RelativelLayout_v2).() -> Unit) =
    _RelativelLayout_v2(context).also(this::addView).also(init)


inline fun <reified T : ViewGroup> T.linearLayout(init: (@DslViewMarker _LinearLayout_v2).() -> Unit) =
    _LinearLayout_v2(context).also(this::addView).also(init)


inline fun <reified T : ViewGroup> T.frameLayout(init: (@DslViewMarker _FrameLayout_v2).() -> Unit) =
    _FrameLayout_v2(context).also(this::addView).also(init)


inline fun <reified T : ViewGroup> T.verticalLayout(init: (@DslViewMarker _LinearLayout_v2).() -> Unit) =
    _LinearLayout_v2(context)
        .also(this::addView)
        .apply {
            orientation = LinearLayout.VERTICAL
            init()
        }

inline fun <reified T : ViewGroup> T.button(init: (@DslViewMarker Button).() -> Unit) =
    Button(context).also(this::addView).also(init)


inline fun <reified T : ViewGroup> T.textView(init: (@DslViewMarker Button).() -> Unit) =
    Button(context).also(this::addView).also(init)


inline fun <reified T : ViewGroup> T.imageView(init: (@DslViewMarker Button).() -> Unit) =
    Button(context).also(this::addView).also(init)

//endregion

//region Activity
inline fun <reified T : Activity> T.relativeLayout(init: (@DslViewMarker _RelativelLayout_v2).() -> Unit) =
    _RelativelLayout_v2(this).also(this::setContentView).also(init)


inline fun <reified T : Activity> T.linearLayout(init: (@DslViewMarker _LinearLayout_v2).() -> Unit) =
    _LinearLayout_v2(this).also(this::setContentView).also(init)


inline fun <reified T : Activity> T.frameLayout(init: (@DslViewMarker _FrameLayout_v2).() -> Unit) =
    _FrameLayout_v2(this).also(this::setContentView).also(init)


inline fun <reified T : Activity> T.verticalLayout(init: (@DslViewMarker _LinearLayout_v2).() -> Unit) =
    _LinearLayout_v2(this)
        .also(this::setContentView)
        .apply {
            orientation = LinearLayout.VERTICAL
            init()
        }

inline fun <reified T : Activity> T.button(init: (@DslViewMarker Button).() -> Unit) =
    Button(this).also(this::setContentView).also(init)


inline fun <reified T : Activity> T.textView(init: (@DslViewMarker Button).() -> Unit) =
    Button(this).also(this::setContentView).also(init)


inline fun <reified T : Activity> T.imageView(init: (@DslViewMarker Button).() -> Unit) =
    Button(this).also(this::setContentView).also(init)

//endregion