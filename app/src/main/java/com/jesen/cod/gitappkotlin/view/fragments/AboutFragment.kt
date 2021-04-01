package com.jesen.cod.gitappkotlin.view.fragments

/**
 * 关于页面，采用Anko Dsl布局
 * */
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import cn.carbs.android.avatarimageview.library.AppCompatAvatarImageView
import com.jesen.cod.gitappkotlin.R
import com.jesen.cod.gitappkotlin.utils.attrStyle
import com.jesen.cod.gitappkotlin.utils.markdownText
import com.jesen.cod.gitappkotlin.view.common.CommonSinglePageFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.sdk15.listeners.onClick
import org.jetbrains.anko.support.v4.nestedScrollView

class AboutFragment : CommonSinglePageFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return AboutFragmentUI()
            .createView(AnkoContext.Companion.create(context!!, this))
    }

}

class AboutFragmentUI : AnkoComponent<AboutFragment> {

    override fun createView(ui: AnkoContext<AboutFragment>): View {
        return ui.apply {
            nestedScrollView {
                verticalLayout {

                    avatarImageView {
                        //为了演示引用第三方库的Theme支持
                        setTextAndColorSeed("Jef", "123")
                    }.lparams(width = dip(30), height = dip(30)) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

                    imageView {
                        imageResource = R.mipmap.ic_launcher_round
                    }.lparams(width = wrapContent, height = wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

                    // 使用主题
                    themedTextView("GitHub", R.style.detail_title) {
                        textColor = attrStyle(R.attr.colorPrimary)
                        padding = dip(10)
                    }.lparams(width = wrapContent, height = wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

                    themedTextView("Jesen0823", R.style.detail_description) {
                        textColor = R.color.colorPrimary
                    }.lparams(width = wrapContent, height = wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

                    // 没有使用主题
                    textView(R.string.open_source_license) {
                        textColor = R.color.colorPrimary

                        onClick {
                            alert {
                                customView {
                                    scrollView {
                                        textView {
                                            padding = dip(10) // dp转dip
                                            // 读取一个文件
                                            markdownText = context.assets.open("licenses.md")
                                                .bufferedReader().readText()
                                        }
                                    }
                                }
                            }.show()
                        }
                    }.lparams(width = wrapContent, height = wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                }.lparams(wrapContent, wrapContent) {
                    gravity = Gravity.CENTER
                }
            }
        }.view
    }
}

/***
 * 为了演示引用第三方库的控件如何使用
 * Copy from:package org.jetbrains.anko
 * 修改返回类型和参数
 * */
inline fun ViewManager.avatarImageView(): AppCompatAvatarImageView = avatarImageView() {}

inline fun ViewManager.avatarImageView(init: (@AnkoViewDslMarker AppCompatAvatarImageView).() -> Unit): AppCompatAvatarImageView {
    return ankoView({ ctx: Context -> AppCompatAvatarImageView(ctx) }, theme = 0) { init() }
}