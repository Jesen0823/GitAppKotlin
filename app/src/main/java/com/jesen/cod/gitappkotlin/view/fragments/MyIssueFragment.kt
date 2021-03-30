package com.jesen.cod.gitappkotlin.view.fragments

import com.jesen.cod.gitappkotlin.entities.Issue
import com.jesen.cod.gitappkotlin.presenter.MyIssuePresenter
import com.jesen.cod.gitappkotlin.view.common.CommonListFragment
import com.jesen.cod.gitappkotlin.view.common.CommonViewPagerFragment
import com.jesen.cod.gitappkotlin.view.config.FragmentPage
import com.jesen.cod.gitappkotlin.view.fragments.subfragments.MyIssueListFragment

class MyIssueFragment : CommonViewPagerFragment() {
    override fun getFragmentPagesLoggedIn(): List<FragmentPage> =
        listOf(FragmentPage(MyIssueListFragment(), "My"))

    override fun getFragmentPagesNotLoggedIn(): List<FragmentPage> =
        listOf(FragmentPage(MyIssueListFragment(), "MY"))


}
