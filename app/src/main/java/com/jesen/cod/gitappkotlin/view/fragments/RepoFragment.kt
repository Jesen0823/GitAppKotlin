package com.jesen.cod.gitappkotlin.view.fragments

import android.os.Bundle
import com.jesen.cod.gitappkotlin.model.account.AccountManager
import com.jesen.cod.gitappkotlin.view.common.CommonViewPagerFragment
import com.jesen.cod.gitappkotlin.view.config.FragmentPage
import com.jesen.cod.gitappkotlin.view.fragments.subfragments.RepoListFragment
import com.jesen.cod.gitappkotlin.view.fragments.subfragments.RepoListFragmentBuilder

class RepoFragment : CommonViewPagerFragment() {

    override fun getFragmentPagesLoggedIn(): List<FragmentPage> {
        return listOf(
            FragmentPage(RepoListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(RepoListFragmentBuilder.OPTIONAL_USER, AccountManager.currentUser)
                }
            }, "My"),
            FragmentPage(RepoListFragment(), "All")
        )
    }

    override fun getFragmentPagesNotLoggedIn(): List<FragmentPage> {
        return listOf(FragmentPage(RepoListFragment(), "All"))
    }
}