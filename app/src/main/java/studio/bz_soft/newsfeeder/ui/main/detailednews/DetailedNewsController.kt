package studio.bz_soft.newsfeeder.ui.main.detailednews

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import studio.bz_soft.mvilibrary.mvi.MVILifecycleController
import studio.bz_soft.newsfeeder.data.Repository
import studio.bz_soft.newsfeeder.data.models.Article
import studio.bz_soft.newsfeeder.root.MainRouter
import studio.bz_soft.newsfeeder.root.Screens

class DetailedNewsController(
    private val router: MainRouter,
    private val repository: Repository,
    private val news: Deferred<Article>
) : MVILifecycleController<DetailedNewsState, DetailedNewsIntent, DetailedNewsRender,
        DetailedNewsAction>(DetailedNewsState()) {

    override suspend fun asyncStart(scope: CoroutineScope) {
        super.asyncStart(scope)
        val article = news.await()
        changeState { it.copy(article = article) }
    }

    override suspend fun onIntent(intent: DetailedNewsIntent) {
        return when (intent) {
            DetailedNewsIntent.Back -> onBackButtonPressed()
            is DetailedNewsIntent.NewsLink -> TODO()
        }
    }

    private fun onBackButtonPressed() {
        router.backTo(Screens.NewsUpdatesScreen)
    }

    override fun renderDiff(oldState: DetailedNewsState, newState: DetailedNewsState): List<DetailedNewsRender> {
        return listOfNotNull(
            newState.takeIf { it != oldState }?.let { DetailedNewsRender.RenderArticle(it.article) }
        )
    }

    override fun renderState(state: DetailedNewsState): List<DetailedNewsRender> {
        return listOfNotNull(
            DetailedNewsRender.RenderArticle(state.article)
        )
    }
}