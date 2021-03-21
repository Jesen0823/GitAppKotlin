package com.jesen.cod.layoutlibrary.v2.test

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.jesen.cod.layoutlibrary.v2.MATCH_PAREN_v2
import com.jesen.cod.layoutlibrary.v2.button
import com.jesen.cod.layoutlibrary.v2.frameLayout
import com.jesen.cod.layoutlibrary.v2.verticalLayout

/**
 * 用来测试自定义的布局SDL框架V2
 * */

class TestActivityV2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        frameLayout {
            setBackgroundColor(Color.CYAN)

            verticalLayout {

                button {
                    text = "Button2"
                    setBackgroundColor(
                        Color.BLACK
                    )
                }.lparams {
                    weight = 1f
                }

                button {
                    text = "Button2"
                    setBackgroundColor(Color.WHITE)
                }.lparams {
                    weight = 3f
                }
            }.lparams(height = MATCH_PAREN_v2) {
                gravity = Gravity.RIGHT
            }
        }
    }
}