import android.annotation.SuppressLint
import android.support.annotation.IdRes
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.view.menu.MenuItemImpl
import android.view.View
import com.jesen.cod.common.ext.otherwise
import com.jesen.cod.common.ext.yes
import com.jesen.cod.gitappkotlin.utils.AppLog

//import com.jesen.cod.gitappkotlin.view.config.NavViewItem

inline fun NavigationView.doOnLayoutAvailable(crossinline black: () -> Unit) {
    ViewCompat.isLaidOut(this).yes {
        // 布局重新绘制
        black()
    }.otherwise {
        addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View?,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                removeOnLayoutChangeListener(this)
                black()
            }
        })
    }
}


/**
 * 选择指定的菜单，并执行相应的操作
 */
@SuppressLint("RestrictedApi")
fun NavigationView.selectItem(@IdRes resId: Int) {
    doOnLayoutAvailable {
        //AppLog.d("selectItem ", "title:${NavViewItem[resId].title}")
        setCheckedItem(resId)
        (menu.findItem(resId) as MenuItemImpl)()
    }

}

inline fun DrawerLayout.afterClosed(crossinline black: () -> Unit) {
    if (isDrawerOpen(GravityCompat.START)) {
        closeDrawer(GravityCompat.START)
        addDrawerListener(
            object : DrawerLayout.DrawerListener {
                override fun onDrawerStateChanged(p0: Int) = Unit

                override fun onDrawerSlide(p0: View, p1: Float) = Unit

                override fun onDrawerOpened(p0: View) = Unit

                override fun onDrawerClosed(p0: View) {
                    removeDrawerListener(this)
                    black()
                }
            }
        )
    } else {
        black()
    }
}