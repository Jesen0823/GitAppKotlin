package com.jesen.cod.gitappkotlin.view.fragments

import android.os.Bundle
import com.jesen.cod.gitappkotlin.model.account.AccountManager
import com.jesen.cod.gitappkotlin.model.people.PeoplePage
import com.jesen.cod.gitappkotlin.model.people.PeoplePage.Type.*
import com.jesen.cod.gitappkotlin.view.common.CommonViewPagerFragment
import com.jesen.cod.gitappkotlin.view.config.FragmentPage
import com.jesen.cod.gitappkotlin.view.fragments.subfragments.PeopleListFragment
import com.jesen.cod.gitappkotlin.view.fragments.subfragments.PeopleListFragmentBuilder
import com.jesen.cod.gitappkotlin.view.fragments.subfragments.RepoListFragment

class PeopleFragment : CommonViewPagerFragment() {

    override fun getFragmentPagesLoggedIn(): List<FragmentPage> = listOf(
        FragmentPage(PeopleListFragment().also {
            it.arguments = Bundle().apply {
                putString(
                    PeopleListFragmentBuilder.OPTIONAL_LOGIN,
                    AccountManager.currentUser?.login
                )
                putString(PeopleListFragmentBuilder.REQUIRED_TYPE, FOLLOWER.name)
            }
        }, "Followers"),

        FragmentPage(PeopleListFragment().also {
            it.arguments = Bundle().apply {
                putString(
                    PeopleListFragmentBuilder.OPTIONAL_LOGIN,
                    AccountManager.currentUser!!.login
                )
                putString(PeopleListFragmentBuilder.REQUIRED_TYPE, FOLLOWER.name)
            }
        }, "Following"),

        FragmentPage(PeopleListFragment().also {
            it.arguments = Bundle().apply {
                putString(PeopleListFragmentBuilder.REQUIRED_TYPE, ALL.name)
            }
        }, "All")
    )

    override fun getFragmentPagesNotLoggedIn(): List<FragmentPage> {
        return listOf(FragmentPage(PeopleListFragment().also {
            it.arguments = Bundle().apply {
                putString(PeopleListFragmentBuilder.REQUIRED_TYPE, ALL.name)
            }
        }, "All"))
    }

}