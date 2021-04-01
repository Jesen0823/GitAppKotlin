package com.jesen.cod.gitappkotlin.entities

import android.os.Parcelable
import com.jesen.cod.common.anno.PoKo
import kotlinx.android.parcel.Parcelize
import retrofit2.adapter.rxjava.PagingWrapper

@PoKo
data class SearchRepositories(
    val incomplete_results: Boolean,
    var items: List<Repository>,
    val total_count: Int
) : PagingWrapper<Repository>() {
    override fun getElements(): List<Repository> {
        return items
    }
}