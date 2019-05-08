package studio.bz_soft.newsfeeder.ui.main.more

import studio.bz_soft.mvilibrary.mvi.MVIFragment
import studio.bz_soft.newsfeeder.root.BackPressedInterface

class MoreFragment : MVIFragment(), BackPressedInterface {


    override fun onBackPressed(): Boolean {
        return true
    }

    companion object {
        fun instance() : MoreFragment = MoreFragment()
    }
}