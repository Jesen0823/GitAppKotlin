package com.jesen.cod.gitappkotlin.view.common

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import com.jesen.cod.gitappkotlin.utils.ViewPagerAdapterList
import com.jesen.cod.gitappkotlin.view.config.FragmentPage

class CommonViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager) {
    val fragmentPages = ViewPagerAdapterList<FragmentPage>(this)

    override fun getItem(position: Int): Fragment {
        return fragmentPages[position].fragment
    }

    override fun getCount(): Int {
        return fragmentPages.size
    }

    override fun getItemPosition(fragment: Any): Int {
        for ((index, page) in fragmentPages.withIndex()) {
            if (fragment == page.fragment) {
                return index
            }
        }
        return PagerAdapter.POSITION_NONE
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentPages[position].title
    }
}