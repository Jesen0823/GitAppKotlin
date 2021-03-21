package com.jesen.cod.layoutlibrary.v1.test

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.jesen.cod.layoutlibrary.v1.MATCH_PARENT
import com.jesen.cod.layoutlibrary.v1.WRAP_CONTENT
import com.jesen.cod.layoutlibrary.v1.button
import com.jesen.cod.layoutlibrary.v1.frameLayout
import com.jesen.cod.layoutlibrary.v1.verticalLayout

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        frameLayout {
            setBackgroundColor(Color.CYAN)

            verticalLayout {
                layoutHeight = MATCH_PARENT
                layoutWidth = WRAP_CONTENT
                layoutGravity = Gravity.RIGHT

                button {
                    text = "Button2"
                    layoutWeight = 1f
                    setBackgroundColor(
                        Color.BLACK
                    )
                }

                button {
                    text = "Button2"
                    layoutWeight = 3f
                    setBackgroundColor(Color.WHITE)
                }
            }
        }
    }
}