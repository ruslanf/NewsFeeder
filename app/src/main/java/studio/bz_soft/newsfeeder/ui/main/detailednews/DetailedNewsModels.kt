package studio.bz_soft.newsfeeder.ui.main.detailednews

import studio.bz_soft.mvilibrary.common.LoadModel
import studio.bz_soft.newsfeeder.data.models.Article
import studio.bz_soft.newsfeeder.data.models.NewsModel

data class DetailedNewsState(
        val news: LoadModel<NewsModel> = LoadModel.Promised(),
        val article: Article = Article()
)

sealed class DetailedNewsIntent {
    object Back : DetailedNewsIntent()
}

sealed class DetailedNewsRender {
    data class RenderArticle(val news: Article) : DetailedNewsRender()
}

sealed class DetailedNewsAction