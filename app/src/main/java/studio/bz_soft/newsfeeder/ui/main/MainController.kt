package studio.bz_soft.newsfeeder.ui.main

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import studio.bz_soft.mvilibrary.common.LoadModel
import studio.bz_soft.mvilibrary.extensions.Err
import studio.bz_soft.mvilibrary.extensions.Ok
import studio.bz_soft.mvilibrary.mvi.MVILifecycleController
import studio.bz_soft.newsfeeder.data.Repository
import studio.bz_soft.newsfeeder.data.models.NewsModel
import studio.bz_soft.newsfeeder.root.Constants
import studio.bz_soft.newsfeeder.root.MainRouter

class MainController(
    private val router: MainRouter,
    private val repository: Repository
) : MVILifecycleController<MainState, MainIntent, MainRender, MainAction>(MainState()) {

    override suspend fun asyncStart(scope: CoroutineScope) {
        getBBCNews()
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
            newState.currentScreen.takeIf { it != oldState.currentScreen }?.let { MainRender.RenderScreen(it) }
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

    private suspend fun getBBCNews() {
        return withState { state ->
            when (state.news) {
                is LoadModel.Promised -> {
                    scope.launch {
                        val listOfNews = when (val r = repository.getBBCNews(Constants.API_ACCESS_KEY)) {
                            is Ok -> LoadModel.Model(r.value)
                            is Err -> LoadModel.Error<NewsModel>(r.error)
                        }
                        changeState { it.copy(news = listOfNews) }
                    }.let { job -> changeState { it.copy(news = LoadModel.Load(job)) } }
                }
            }
        }
    }
}