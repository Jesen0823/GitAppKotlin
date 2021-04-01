package com.jesen.cod.gitappkotlin.view.common

import android.view.View
import com.jesen.cod.gitappkotlin.model.page.ListPage
import com.jesen.cod.gitappkotlin.utils.AppLog
import com.jesen.cod.mvp.impl.BaseFragment
import com.jesen.cod.mvp.impl.BasePresenter
import rx.Subscription

private const val TAG = "CommonListPresenter"

abstract class CommonListPresenter<DataType, out View : CommonListFragment<DataType,
        CommonListPresenter<DataType, View>>> : BasePresenter<View>() {

    abstract val listPage: ListPage<DataType>

    private var firstInView = true
    private val subscriptionList = ArrayList<Subscription>()

    fun initData() {
        listPage.loadFromFirst()
            .subscribe({
                //AppLog.i(TAG,"initData it:${it.toString()}")
                if (it.isEmpty()) view.onDataInitWithNothing() else view.onDataInit(it)
            }, {
                AppLog.i(TAG, "initData error:${it.toString()} \n ${it.printStackTrace()}")
                view.onDataInitWithError(it.message ?: it.toString())
            }).let(subscriptionList::add)
    }

    fun refreshData() {
        listPage.loadFromFirst()
            .subscribe({
                if (it.isEmpty()) view.onDataInitWithNothing() else view.onDataRefresh(it)
            }, {
                view.onDataRefreshWithError(it.message ?: it.toString())
            }).let(subscriptionList::add)
    }

    fun loadMore() {
        listPage.loadMore()
            .subscribe({
                view.onMoreDataLoaded(it)
            }, {
                view.onMoreDataLoadedWithError(it.message ?: it.toString())
            }).let(subscriptionList::add)
    }

    override fun onResume() {
        super.onResume()
        if (!firstInView) {
            refreshData()
        }
        firstInView = false
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptionList.forEach(Subscription::unsubscribe)
        subscriptionList.clear()
    }
}