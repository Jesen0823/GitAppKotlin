package com.jesen.cod.gitappkotlin.presenter

import com.jesen.cod.gitappkotlin.entities.Issue
import com.jesen.cod.gitappkotlin.model.issue.MyIssuePage
import com.jesen.cod.gitappkotlin.model.page.ListPage
import com.jesen.cod.gitappkotlin.view.common.CommonListPresenter
import com.jesen.cod.gitappkotlin.view.fragments.subfragments.MyIssueListFragment

class MyIssuePresenter : CommonListPresenter<Issue, MyIssueListFragment>() {
    override val listPage = MyIssuePage()
}