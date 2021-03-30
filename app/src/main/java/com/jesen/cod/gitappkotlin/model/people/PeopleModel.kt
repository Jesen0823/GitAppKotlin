package com.jesen.cod.gitappkotlin.model.people

import com.jesen.cod.gitappkotlin.entities.User2
import com.jesen.cod.gitappkotlin.model.page.ListPage
import com.jesen.cod.gitappkotlin.network.service.UserService
import retrofit2.adapter.rxjava.GitHubPaging
import rx.Observable

class PeoplePageParams(val type: String, val login: String?)

class PeoplePage(val params: PeoplePageParams) : ListPage<User2>() {
    enum class Type {
        FOLLOWER, FOLLOWING, ALL
    }

    override fun getData(page: Int): Observable<GitHubPaging<User2>> {
        return when (Type.valueOf(params.type)) {
            PeoplePage.Type.FOLLOWER -> UserService.followers(params.login!!, page = page)
            PeoplePage.Type.FOLLOWING -> UserService.following(params.login!!, page = page)
            PeoplePage.Type.ALL -> UserService.allUsers(data.since)
        }
    }

}