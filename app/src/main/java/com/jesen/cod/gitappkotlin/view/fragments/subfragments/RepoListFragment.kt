package com.jesen.cod.gitappkotlin.view.fragments.subfragments

import com.bennyhuo.tieguanyin.annotations.FragmentBuilder
import com.bennyhuo.tieguanyin.annotations.Optional
import com.jesen.cod.gitappkotlin.entities.Repository
import com.jesen.cod.gitappkotlin.entities.User2
import com.jesen.cod.gitappkotlin.presenter.RepoListPresenter
import com.jesen.cod.gitappkotlin.view.common.CommonListFragment

@FragmentBuilder
class RepoListFragment : CommonListFragment<Repository, RepoListPresenter>() {

    @Optional
    var user: User2? = null

    override val adapter = RepoListAdapter()
}