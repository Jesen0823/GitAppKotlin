package com.jesen.cod.gitappkotlin.view.fragments.subfragments

import com.bennyhuo.tieguanyin.annotations.FragmentBuilder
import com.bennyhuo.tieguanyin.annotations.Optional
import com.bennyhuo.tieguanyin.annotations.Required
import com.jesen.cod.gitappkotlin.entities.User2
import com.jesen.cod.gitappkotlin.presenter.PeopleListPresenter
import com.jesen.cod.gitappkotlin.view.common.CommonListFragment

@FragmentBuilder
class PeopleListFragment : CommonListFragment<User2, PeopleListPresenter>() {

    @Optional
    lateinit var login: String

    @Required
    lateinit var type: String

    override val adapter = PeopleListAdapter()

}