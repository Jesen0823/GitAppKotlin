package com.jesen.cod.mvp.impl

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jesen.cod.mvp.IMvpView
import com.jesen.cod.mvp.IPresenter
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

abstract class BaseFragment<out P : BasePresenter<BaseFragment<P>>> : IMvpView<P>, Fragment() {
    override val presenter: P

    init {
        presenter = createPresenterKt()
        presenter.view = this
    }

    private fun createPresenterKt(): P {
        sequence {
            var thisClass: KClass<*> = this@BaseFragment::class
            while (true) {
                yield(thisClass.supertypes)
                thisClass = thisClass.supertypes.firstOrNull()?.jvmErasure ?: break
            }
        }.flatMap {
            it.flatMap {
                it.arguments // 拿到泛型实参
            }.asSequence()  // 拿到后生成序列
        }.first {
            it.type?.jvmErasure?.isSubclassOf(IPresenter::class) ?: false
        }.let {
            return it.type!!.jvmErasure.primaryConstructor!!.call() as P
        }
    }

    private fun createPresenterJava(): P {
        sequence {
            var thisClass: Class<*> = this@BaseFragment.javaClass
            while (true) {
                yield(thisClass.genericSuperclass!!)
                thisClass = thisClass.superclass ?: break
            }
        }.filter {
            it is ParameterizedType
        }.flatMap {
            (it as ParameterizedType).actualTypeArguments.asSequence()
        }.first {
            it is Class<*> && IPresenter::class.java.isAssignableFrom(it)
        }.let {
            return (it as Class<P>).newInstance()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        presenter.onViewStateRestored(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        presenter.onConfigurationChanged(newConfig)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }
}

class MainFragment : BaseFragment<MainPresenter>()
class MainPresenter : BasePresenter<MainFragment>()