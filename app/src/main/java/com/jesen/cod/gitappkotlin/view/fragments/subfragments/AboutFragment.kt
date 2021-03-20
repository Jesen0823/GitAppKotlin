package com.jesen.cod.gitappkotlin.view.fragments.subfragments

/**
 * 关于页面，采用Anko Dsl布局
 * */
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jesen.cod.gitappkotlin.R
import com.jesen.cod.gitappkotlin.utils.markdownText
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk15.listeners.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.nestedScrollView

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return AboutFragmentUI().createView(AnkoContext.Companion.create(context!!, this))
    }

}

class AboutFragmentUI : AnkoComponent<AboutFragment> {

    override fun createView(ui: AnkoContext<AboutFragment>): View {
        return ui.apply {
            nestedScrollView {
                verticalLayout {
                    imageView {
                        imageResource = R.mipmap.ic_launcher_round
                    }.lparams(width = wrapContent, height = wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                    textView("GitHub") {
                        textColor = R.color.colorPrimary
                        padding = dip(10)
                    }.lparams(width = wrapContent, height = wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

                    textView("Jesen0823") {
                        textColor = R.color.colorPrimary
                    }.lparams(width = wrapContent, height = wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

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
