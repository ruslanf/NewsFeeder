package studio.bz_soft.newsfeeder.ui.main

import kotlinx.coroutines.CoroutineScope
import studio.bz_soft.mvilibrary.mvi.MVILifecycleController
import studio.bz_soft.newsfeeder.root.MainRouter

class MainController(
        private val router: MainRouter
) : MVILifecycleController<MainState, MainIntent, MainRender, MainAction>(MainState()) {

    override suspend fun asyncStart(scope: CoroutineScope) {
        super.asyncStart(scope)
    }

    override suspend fun onIntent(intent: MainIntent) {
        return when (intent) {
            MainIntent.Back -> onBackPressed()
            is MainIntent.OnNavigate -> onNavigate(intent.screen)
        }
    }

    override fun renderDiff(oldState: MainState, newState: MainState): List<MainRender> {
        return listOfNotNull(
                newState.currentScreen.takeIf { it != oldState.currentScreen }?.let {
                    MainRender.RenderScreen(it)
                }
        )
    }

    override fun renderState(state: MainState): List<MainRender> {
        return listOfNotNull(
                MainRender.RenderScreen(state.currentScreen)
        )
    }

    private fun onBackPressed() {
        router.exit()
    }

    private suspend fun onNavigate(screen: MainScreens) {
        changeState { it.copy(currentScreen = screen) }
    }
}