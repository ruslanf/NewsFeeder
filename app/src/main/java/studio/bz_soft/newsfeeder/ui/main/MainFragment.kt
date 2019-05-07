package studio.bz_soft.newsfeeder.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main.view.*
import studio.bz_soft.newsfeeder.R
import studio.bz_soft.newsfeeder.root.BackPressedInterface

class MainFragment : Fragment(), BackPressedInterface {

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

                        true
                    }
                    R.id.menuTitleMore -> {
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