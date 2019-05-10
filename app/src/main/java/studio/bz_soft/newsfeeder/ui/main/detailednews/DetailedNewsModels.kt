package studio.bz_soft.newsfeeder.ui.main.detailednews

import studio.bz_soft.newsfeeder.data.models.Article

data class DetailedNewsState(
        val article: Article = Article()
)

sealed class DetailedNewsIntent {
    object Back : DetailedNewsIntent()
}

sealed class DetailedNewsRender {
    data class RenderArticle(val news: Article) : DetailedNewsRender()
}

sealed class DetailedNewsAction