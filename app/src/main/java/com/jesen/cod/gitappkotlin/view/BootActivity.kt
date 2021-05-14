package com.jesen.cod.gitappkotlin.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView
import com.jesen.cod.gitappkotlin.R
import com.jesen.cod.gitappkotlin.utils.simpleStartActivity
import kotlinx.android.synthetic.main.activity_boot.*

class BootActivity : AppCompatActivity() {
    lateinit var mLottieAnim: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boot)

        mLottieAnim = findViewById(R.id.lottie_anim_view)
        mLottieAnim.addAnimatorUpdateListener {
            if (it.animatedFraction == 1.0f) {
                startMainPage()
            }
        }

        skipBtn.setOnClickListener {
            mLottieAnim.pauseAnimation()
            startMainPage()
        }
    }

    private fun startMainPage() {
        simpleStartActivity<MainActivity>(this) {}
        finish()
    }
}
