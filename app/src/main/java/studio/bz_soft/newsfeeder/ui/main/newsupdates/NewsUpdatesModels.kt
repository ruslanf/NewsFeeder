package studio.bz_soft.newsfeeder.ui.main.newsupdates

import studio.bz_soft.mvilibrary.common.LoadModel
import studio.bz_soft.newsfeeder.data.models.Article
import studio.bz_soft.newsfeeder.data.models.NewsModel

data class NewsUpdatesState(
        val news: LoadModel<NewsModel> = LoadModel.Promised(),
        val isRefreshing: Boolean = false
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
    data class SwipeRefreshRender(val isRefreshing: Boolean) : NewsUpdatesRender()
}