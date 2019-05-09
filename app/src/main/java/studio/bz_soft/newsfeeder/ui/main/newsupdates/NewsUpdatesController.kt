package studio.bz_soft.newsfeeder.ui.main.newsupdates

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import studio.bz_soft.mvilibrary.common.LoadModel
import studio.bz_soft.mvilibrary.extensions.Err
import studio.bz_soft.mvilibrary.extensions.Ok
import studio.bz_soft.mvilibrary.mvi.MVILifecycleController
import studio.bz_soft.newsfeeder.data.Repository
import studio.bz_soft.newsfeeder.data.models.Article
import studio.bz_soft.newsfeeder.data.models.NewsModel
import studio.bz_soft.newsfeeder.root.Constants
import studio.bz_soft.newsfeeder.root.MainRouter
import studio.bz_soft.newsfeeder.root.Screens

class NewsUpdatesController(
        private val router: MainRouter,
        private val repository: Repository
) : MVILifecycleController<NewsUpdatesState, NewsUpdatesIntent,
        NewsUpdatesRender, NewsUpdatesAction>(NewsUpdatesState()) {

    override suspend fun asyncStart(scope: CoroutineScope) {
        super.asyncStart(scope)
        getBBCNews()
    }

    override suspend fun onIntent(intent: NewsUpdatesIntent) {
        return when (intent) {
            NewsUpdatesIntent.Back -> onBackPressed()
            is NewsUpdatesIntent.SelectNews -> router.navigateTo(Screens.DetailedNewsScreen(intent.news))
        }
    }

    override fun renderDiff(oldState: NewsUpdatesState, newState: NewsUpdatesState): List<NewsUpdatesRender> {
        return listOfNotNull(
                showProgressBar(newState).takeIf { it != showProgressBar(oldState) }?.let {
                    NewsUpdatesRender.ProgressBarRender(it)
                },
                showNews(newState).takeIf { it != showNews(oldState) }?.let {
                    NewsUpdatesRender.ListNewsRender(it)
                }
        )
    }

    override fun renderState(state: NewsUpdatesState): List<NewsUpdatesRender> {
        return listOfNotNull(
                NewsUpdatesRender.ListNewsRender(showNews(state)),
                NewsUpdatesRender.ProgressBarRender(showProgressBar(state))
        )
    }

    private fun showNews(state: NewsUpdatesState): List<Article> {
        return when (val news = state.news) {
            is LoadModel.Model -> news.value.listOfArticles
            else -> emptyList()
        }
    }

    private fun showProgressBar(state: NewsUpdatesState): Boolean {
        return state.news is LoadModel.Promised || state.news is LoadModel.Load
    }

    private fun onBackPressed() {
        router.exit()
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