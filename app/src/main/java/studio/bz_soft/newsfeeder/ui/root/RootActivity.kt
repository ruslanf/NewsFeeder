package studio.bz_soft.newsfeeder.ui.root

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import studio.bz_soft.newsfeeder.R
import studio.bz_soft.newsfeeder.root.BackPressedInterface
import studio.bz_soft.newsfeeder.root.Screens

class RootActivity : AppCompatActivity() {

    private val router by inject<Router>()
    private val navigatorHolder by inject<NavigatorHolder>()
    private val fragmentNavigator = SupportAppNavigator(this, supportFragmentManager, R.id.flRoot)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_root)
        if (savedInstanceState == null) {
            router.newRootScreen(Screens.MainScreen)
        }
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(fragmentNavigator)
    }

    override fun onBackPressed() {
        when (val supportFM = supportFragmentManager.findFragmentById(R.id.flRoot)) {
            is BackPressedInterface -> when (supportFM.onBackPressed()) { false -> router.exit() }
            else -> router.exit()
        }
    }
}
