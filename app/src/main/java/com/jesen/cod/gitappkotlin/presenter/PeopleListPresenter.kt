package com.jesen.cod.gitappkotlin.presenter

import com.jesen.cod.gitappkotlin.entities.User2
import com.jesen.cod.gitappkotlin.model.page.ListPage
import com.jesen.cod.gitappkotlin.model.people.PeoplePage
import com.jesen.cod.gitappkotlin.model.people.PeoplePageParams
import com.jesen.cod.gitappkotlin.view.common.CommonListPresenter
import com.jesen.cod.gitappkotlin.view.fragments.subfragments.PeopleListFragment

class PeopleListPresenter : CommonListPresenter<User2, PeopleListFragment>() {
    override val listPage: ListPage<User2> by lazy {
        PeoplePage(PeoplePageParams(view.type, view.login))
    }
}