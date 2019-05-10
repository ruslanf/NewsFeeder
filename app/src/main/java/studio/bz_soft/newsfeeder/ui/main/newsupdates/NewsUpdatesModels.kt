package studio.bz_soft.newsfeeder.ui.main.newsupdates

import studio.bz_soft.mvilibrary.common.LoadModel
import studio.bz_soft.newsfeeder.data.models.Article
import studio.bz_soft.newsfeeder.data.models.NewsModel

enum class RefreshStatus {NOT_REFRESHED, REFRESHING, REFRESHED}

data class NewsUpdatesState(
        val news: LoadModel<NewsModel> = LoadModel.Promised(),
        val refreshing: RefreshStatus = RefreshStatus.NOT_REFRESHED
)

sealed class NewsUpdatesIntent {
    object Back : NewsUpdatesIntent()
    object Refresh : NewsUpdatesIntent()
    data class SelectNews(val news: Article) : NewsUpdatesIntent()
}

sealed class NewsUpdatesAction

sealed class NewsUpdatesRender {
    data class ListNewsRender(val news: List<Article>) : NewsUpdatesRender()
    data class ProgressBarRender(val isShown: Boolean) : NewsUpdatesRender()
    data class SwipeRefreshRender(val refreshing: RefreshStatus) : NewsUpdatesRender()
}