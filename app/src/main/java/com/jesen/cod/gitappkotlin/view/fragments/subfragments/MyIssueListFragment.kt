package com.jesen.cod.gitappkotlin.view.fragments.subfragments

import com.jesen.cod.gitappkotlin.entities.Issue
import com.jesen.cod.gitappkotlin.presenter.MyIssuePresenter
import com.jesen.cod.gitappkotlin.view.common.CommonListAdapter
import com.jesen.cod.gitappkotlin.view.common.CommonListFragment

class MyIssueListFragment : CommonListFragment<Issue, MyIssuePresenter>() {
    companion object {
        const val REPOSITORY_NAME = "repository_name"
        const val OWNER_LOGIN = "owner_login"
    }

    override val adapter = IssueListAdapter()
}