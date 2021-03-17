package com.jesen.cod.gitappkotlin.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import com.bennyhuo.tieguanyin.annotations.ActivityBuilder
import com.jesen.cod.gitappkotlin.R
import com.jesen.cod.gitappkotlin.entities.User2
import com.jesen.cod.gitappkotlin.model.account.AccountManager
import com.jesen.cod.gitappkotlin.model.account.OnAccountStateChangeListener
import com.jesen.cod.gitappkotlin.utils.loadWithGlide
import doOnLayoutAvailable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.jetbrains.anko.imageResource

@ActivityBuilder(flags = [Intent.FLAG_ACTIVITY_CLEAR_TOP])
class MainActivity : AppCompatActivity(), OnAccountStateChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, m_toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.setDrawerListener(toggle)
        toggle.syncState()
        initNavigationView()
        AccountManager.onAccountStateChangeListeners.add(this)
    }

    override fun onDestroy() {
        AccountManager.onAccountStateChangeListeners.remove(this)
        super.onDestroy()
    }

    private fun initNavigationView() {
        AccountManager.currentUser?.let(::updateNavigationView) ?: clearNavigationView()
    }

    private fun updateNavigationView(user2: User2) {
        navigationView.doOnLayoutAvailable {
            usernameView.text = user2.login
            emailView.text = user2.email ?: ""
            avatarView.loadWithGlide(user2.avatar_url, user2.login.first())
        }
    }

    private fun clearNavigationView() {
        navigationView.doOnLayoutAvailable {
            usernameView.text = resources.getString(R.string.please_login)
            emailView.text = ""
            avatarView.imageResource = R.drawable.ic_github
        }
    }

    override fun onLogin(user2: User2) {
        updateNavigationView(user2)
    }

    override fun onLogout() {
        clearNavigationView()
    }
}
