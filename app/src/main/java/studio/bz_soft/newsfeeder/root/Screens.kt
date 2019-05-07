package studio.bz_soft.newsfeeder.root

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import studio.bz_soft.newsfeeder.ui.main.MainFragment
import studio.bz_soft.newsfeeder.ui.main.newsupdates.NewsUpdatesFragment

sealed class Screens : SupportAppScreen() {

    object MainScreen : Screens() {
        override fun getFragment(): Fragment = MainFragment.instance()
    }

    object NewsUpdatesScreen : Screens() {
        override fun getFragment(): Fragment = NewsUpdatesFragment.instance()
    }
}