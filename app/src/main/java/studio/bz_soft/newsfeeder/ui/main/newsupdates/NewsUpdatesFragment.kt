package studio.bz_soft.newsfeeder.ui.main.newsupdates

import androidx.fragment.app.Fragment
import studio.bz_soft.newsfeeder.root.BackPressedInterface

class NewsUpdatesFragment : Fragment(), BackPressedInterface {

    override fun onBackPressed(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun instance() : NewsUpdatesFragment = NewsUpdatesFragment()
    }
}