package com.jesen.cod.gitappkotlin.model.page

import com.jesen.cod.common.log.logger
import com.jesen.cod.gitappkotlin.utils.AppLog
import retrofit2.adapter.rxjava.GitHubPaging
import rx.Observable

private const val TAG = "ListPage"

abstract class ListPage<DataType> : DataProvider<DataType> {
    companion object {
        // 默认分页大小
        const val PAGE_SIZE = 20
    }

    var currentPage = 1
        private set

    val data = GitHubPaging<DataType>()

    fun loadMore() = getData(currentPage + 1)
        .doOnNext {
            currentPage += 1
        }
        .doOnError {
            AppLog.i(TAG, "load more error:" + it)
        }
        .map {
            data.mergeData(it)
            data
        }

    fun loadFromFirst(pageCount: Int = currentPage) =
        Observable.range(1, pageCount)
            .concatMap {
                // 并行请求
                getData(it)
            }
            .doOnError {
                logger.error("loadFromFirst, pageCount=$pageCount", it)
            }
            // acc 总共的数据
            .reduce { acc, page ->
                acc.mergeData(page)
            }
            .doOnNext {
                data.clear()
                data.mergeData(it)
            }
}