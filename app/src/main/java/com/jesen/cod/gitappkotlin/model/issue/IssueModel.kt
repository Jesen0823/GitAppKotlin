package com.jesen.cod.gitappkotlin.model.issue

import com.jesen.cod.gitappkotlin.entities.Issue
import com.jesen.cod.gitappkotlin.model.page.ListPage
import com.jesen.cod.gitappkotlin.network.service.IssueService
import retrofit2.adapter.rxjava.GitHubPaging
import rx.Observable

class MyIssuePage : ListPage<Issue>() {

    override fun getData(page: Int): Observable<GitHubPaging<Issue>> {
        return IssueService.listIssuesOfAuthenticatedUser(page = page)
    }
}