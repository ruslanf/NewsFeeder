package studio.bz_soft.newsfeeder.ui.main.newsupdates

import studio.bz_soft.mvilibrary.mvi.MVIFragment
import studio.bz_soft.newsfeeder.root.BackPressedInterface

class NewsUpdatesFragment : MVIFragment(), BackPressedInterface {

    override fun onBackPressed(): Boolean {
        return true
    }

    companion object {
        fun instance() : NewsUpdatesFragment = NewsUpdatesFragment()
    }
}