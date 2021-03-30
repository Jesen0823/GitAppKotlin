package com.jesen.cod.gitappkotlin.entities

import retrofit2.adapter.rxjava.PagingWrapper

data class SearchRepositories(
    var total_count: Int,
    var incomplete_results: Boolean,
    var items: List<Repository>
) : PagingWrapper<Repository>() {
    override fun getElements(): List<Repository> {
        return items
    }
}