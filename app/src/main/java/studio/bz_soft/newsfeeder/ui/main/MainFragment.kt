package studio.bz_soft.newsfeeder.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.view.*
import org.koin.android.ext.android.get
import studio.bz_soft.mvilibrary.mvi.MVIFragment
import studio.bz_soft.newsfeeder.R
import studio.bz_soft.newsfeeder.root.BackPressedInterface
import studio.bz_soft.newsfeeder.ui.main.more.MoreFragment
import studio.bz_soft.newsfeeder.ui.main.newsupdates.NewsUpdatesFragment

class MainFragment : MVIFragment(), BackPressedInterface {

    private val controller: MainController by registered(
        { v, r -> render(v, r) },
        { v, a -> action(v, a) }
    ) { MainController(get(), get()) }

    private val newsUpdatesFragment = NewsUpdatesFragment.instance()
    private val moreFragment = MoreFragment.instance()

    private fun render(v: View, r: MainRender) {
        return when (r) {
            is MainRender.RenderScreen -> renderFragment(v, r.screen)
        }
    }

    private fun renderFragment(v: View, screen: MainScreens) {
        val currentFragment = childFragmentManager.findFragmentById(v.flMain.id)
        when {
            currentFragment is NewsUpdatesFragment && screen is MainScreens.NewsUpdates -> return
            currentFragment is MoreFragment && screen is MainScreens.More -> return
        }

        val fragment: Fragment = when (screen) {
            is MainScreens.NewsUpdates -> newsUpdatesFragment
            is MainScreens.More -> moreFragment
        }
        childFragmentManager.beginTransaction()
            .hide(newsUpdatesFragment)
            .hide(moreFragment)
            .show(fragment)
            .commit()
    }

    private fun action(v: View, a: MainAction) {

    }

    private fun getCurrentFragment(): Fragment? =
        view?.let {
            childFragmentManager.findFragmentById(it.flMain.id)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            mainBottomNavigationMenu.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.menuTitleNewsUpdates -> {
                        controller.sendIntent(MainIntent.OnNavigate(MainScreens.NewsUpdates))
                        true
                    }
                    R.id.menuTitleMore -> {
                        controller.sendIntent(MainIntent.OnNavigate(MainScreens.More))
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onBackPressed(): Boolean =
        (getCurrentFragment() as? BackPressedInterface)?.onBackPressed() ?: false

    companion object {
        fun instance(): MainFragment = MainFragment()
    }
}