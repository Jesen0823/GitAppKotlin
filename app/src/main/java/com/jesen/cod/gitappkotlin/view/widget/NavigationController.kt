package com.jesen.cod.gitappkotlin.view.widget

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.support.design.widget.NavigationView
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.view.MenuItem
import com.jesen.cod.common.log.logger
import com.jesen.cod.gitappkotlin.R
import com.jesen.cod.gitappkotlin.entities.User2
import com.jesen.cod.gitappkotlin.model.account.AccountManager
import com.jesen.cod.gitappkotlin.setting.Settings
import com.jesen.cod.gitappkotlin.utils.loadWithGlide
import com.jesen.cod.gitappkotlin.view.config.NavViewItem
import doOnLayoutAvailable
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.jetbrains.anko.Android
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk15.listeners.onClick
import selectItem

class NavigationController(
    private val navigationView: NavigationView,
    private val onNavItemChanged: (NavViewItem) -> Unit,
    private val onHeaderClick: () -> Unit
) : NavigationView.OnNavigationItemSelectedListener {

    init {
        navigationView.setNavigationItemSelectedListener(this)
    }

    private var currentItem: NavViewItem? = null

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigationView.apply {
            Settings.lastPage = item.itemId
            val navItem = NavViewItem[item.itemId]
            onNavItemChanged(navItem)
        }
        return true
    }

    fun useLoginLayout() {
        navigationView.menu.clear()
        navigationView.inflateMenu(R.menu.activity_main_drawer)
        onUpdate(AccountManager.currentUser)
        selectProperItem()
    }

    fun useNoLoginLayout() {
        navigationView.menu.clear()
        navigationView.inflateMenu(R.menu.activity_main_drawer_no_logged_in)
        onUpdate(AccountManager.currentUser)
        selectProperItem()
    }

    private fun onUpdate(user: User2?) {
        navigationView.doOnLayoutAvailable {
            navigationView.apply {
                usernameView.text = user?.login ?: "请登录"
                emailView.text = user?.email ?: "xie_stacol@163.com"
                if (user == null) {
                    avatarView.imageResource = R.drawable.ic_github
                } else {
                    avatarView.loadWithGlide(user.avatar_url, user.login.first())
                }
                navigationHeader.onClick {
                    onHeaderClick()
                }
            }
        }
    }

    fun selectProperItem() {
        logger.debug("selectProperItem onLayout: $currentItem")
        val page = currentItem?.let { NavViewItem[it] } ?: Settings.lastPage

        (page.takeIf { navigationView.menu.findItem(it) != null } ?: kotlin.run {
            Settings.defaultPage
        }).let(navigationView::selectItem)
    }
}