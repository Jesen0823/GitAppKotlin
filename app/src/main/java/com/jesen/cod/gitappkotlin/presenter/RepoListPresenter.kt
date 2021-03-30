package com.jesen.cod.gitappkotlin.presenter

import android.arch.lifecycle.ReportFragment
import com.jesen.cod.gitappkotlin.entities.Repository
import com.jesen.cod.gitappkotlin.model.page.ListPage
import com.jesen.cod.gitappkotlin.model.repo.RepoListPage
import com.jesen.cod.gitappkotlin.view.common.CommonListPresenter
import com.jesen.cod.gitappkotlin.view.fragments.subfragments.RepoListFragment

class RepoListPresenter : CommonListPresenter<Repository, RepoListFragment>() {
    override val listPage by lazy {
        RepoListPage(view.user)
    }
}