package com.jesen.cod.gitappkotlin.view

import afterClosed
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.widget.Switch
import com.bennyhuo.tieguanyin.annotations.ActivityBuilder
import com.jesen.cod.common.ext.no
import com.jesen.cod.common.ext.otherwise
import com.jesen.cod.common.ext.yes
import com.jesen.cod.gitappkotlin.R
import com.jesen.cod.gitappkotlin.entities.User2
import com.jesen.cod.gitappkotlin.model.account.AccountManager
import com.jesen.cod.gitappkotlin.model.account.OnAccountStateChangeListener
import com.jesen.cod.gitappkotlin.network.service.RepositoryService
import com.jesen.cod.gitappkotlin.setting.Settings
import com.jesen.cod.gitappkotlin.utils.AppLog
import com.jesen.cod.gitappkotlin.utils.loadWithGlide
import com.jesen.cod.gitappkotlin.utils.showFragment
import com.jesen.cod.gitappkotlin.utils.simpleStartActivity
import com.jesen.cod.gitappkotlin.view.config.NavViewItem
import com.jesen.cod.gitappkotlin.view.config.Themer
import com.jesen.cod.gitappkotlin.view.widget.ActionBarController
import com.jesen.cod.gitappkotlin.view.widget.NavigationController
import doOnLayoutAvailable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk15.listeners.onClick
import org.jetbrains.anko.toast

@ActivityBuilder(flags = [Intent.FLAG_ACTIVITY_CLEAR_TOP])
class MainActivity : AppCompatActivity(), OnAccountStateChangeListener {

    private val TAG = "MainActivity"

    val actionBarController by lazy {
        ActionBarController(this)
    }

    private val navigationController by lazy {
        NavigationController(navigationView, ::onNavItemChanged, ::handleNavigationHeaderClickEvent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Themer.applyProperTheme(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(m_toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, m_toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.setDrawerListener(toggle)
        toggle.syncState()

        initNavigationView()
        AccountManager.onAccountStateChangeListeners.add(this)

        /*showFragment(R.id.fragmentContainer, AboutFragment::class.java)
        title = "About"*/

        /* RepositoryService.listRepositoriesOfUser("enbandari",2)
             .subscribe({
                 AppLog.d(TAG,"hasNext : ${it.hasNext}")
                 AppLog.d(TAG,"hasPrev : ${it.hasPrev}")

             },{

             })*/
    }

    override fun onDestroy() {
        AccountManager.onAccountStateChangeListeners.remove(this)
        super.onDestroy()
    }

    private fun initNavigationView() {
        AccountManager.isLoggedIn()
            .yes {
                navigationController.useLoginLayout()
            }
            .otherwise {
                navigationController.useNoLoginLayout()
            }
        navigationController.selectProperItem()
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

    private fun initNavigationHeaderView() {
        navigationView.onClick {
            AppLog.d(TAG, "on click")
            //AccountManager.isLoggedIn().no {
                simpleStartActivity<LoginActivity>(this) {
                    setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                }
            /*}.otherwise {
                AccountManager.logout().subscribe({
                    toast("注销成功")
                }, {
                    it.printStackTrace()
                })
            }*/
        }
    }

    override fun onLogin(user2: User2) {
        updateNavigationView(user2)
    }

    override fun onLogout() {
        clearNavigationView()
    }

    override fun onBackPressed() {
        (drawer_layout.isDrawerOpen(GravityCompat.START)).yes {
            drawer_layout.closeDrawer(GravityCompat.START)
        }.otherwise {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_actionbar, menu)
        val view = menu.findItem(R.id.dayNight).actionView
        var switchView = view.findViewById<Switch>(R.id.dayNightSwitch)
        switchView.apply {

            // isChecked = Settings.themeMode == Themer.ThemeMode.NIGHT.name
            isChecked = Themer.currentTheme() == Themer.ThemeMode.DAY
            setSwitchTextAppearance(this@MainActivity, R.style.s_day)
            setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    setSwitchTextAppearance(this@MainActivity, R.style.s_night)
                } else {
                    setSwitchTextAppearance(this@MainActivity, R.style.s_day)
                }
                Themer.toggle(this@MainActivity);
            }
        }

        return true
    }

    private fun onNavItemChanged(navViewItem: NavViewItem) {
        drawer_layout.afterClosed {
            showFragment(R.id.fragmentContainer, navViewItem.fragmentClass, navViewItem.arguments)
            title = navViewItem.title
        }
    }

    private fun handleNavigationHeaderClickEvent() {
        AccountManager.isLoggedIn().no {
            startLoginActivity()
        }.otherwise {
            AccountManager.logout().subscribe({
                toast("注销成功")
            }, {
                it.printStackTrace()
            })
        }
    }

    private fun startLoginActivity() {
        simpleStartActivity<LoginActivity>(this) {
            //setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        }
    }
}
