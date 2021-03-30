package com.jesen.cod.gitappkotlin.model.repo

import com.jesen.cod.gitappkotlin.entities.Repository
import com.jesen.cod.gitappkotlin.entities.User2
import com.jesen.cod.gitappkotlin.model.page.ListPage
import com.jesen.cod.gitappkotlin.network.service.RepositoryService
import com.jesen.cod.gitappkotlin.utils.format
import retrofit2.adapter.rxjava.GitHubPaging
import rx.Observable
import java.util.*

/**
 * 仓库列表
 * **/
class RepoListPage(val owner: User2?) : ListPage<Repository>() {
    override fun getData(page: Int): Observable<GitHubPaging<Repository>> {
        return if (owner == null) {
            RepositoryService.allRepositories(page, "pushed:<" + Date().format("yyyy-MM-dd"))
                .map { it.paging }
        } else {
            RepositoryService.listRepositoriesOfUser(owner.login, page)
        }
    }

}