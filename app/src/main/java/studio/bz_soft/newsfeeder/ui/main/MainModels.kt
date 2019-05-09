package studio.bz_soft.newsfeeder.ui.main

import studio.bz_soft.mvilibrary.common.LoadModel
import studio.bz_soft.newsfeeder.data.models.NewsModel

data class MainState(
    val news: LoadModel<NewsModel> = LoadModel.Promised(),
    val currentScreen: MainScreens = MainScreens.NewsUpdates
)

sealed class MainIntent {
    object Back : MainIntent()
    data class OnNavigate(val screen: MainScreens) : MainIntent()
}

sealed class MainAction

sealed class MainRender {
    data class RenderScreen(val screen: MainScreens) : MainRender()
}

sealed class MainScreens {
    object NewsUpdates : MainScreens()
    object More : MainScreens()
}