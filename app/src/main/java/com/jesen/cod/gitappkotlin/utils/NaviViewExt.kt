import android.support.design.widget.NavigationView
import android.support.v4.view.ViewCompat
import com.jesen.cod.common.ext.yes

inline fun NavigationView.doOnLayoutAvailable(crossinline black: () -> Unit) {
    ViewCompat.isLaidOut(this).yes {

    }
}