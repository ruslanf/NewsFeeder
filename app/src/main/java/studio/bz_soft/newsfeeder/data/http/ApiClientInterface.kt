package studio.bz_soft.newsfeeder.data.http

import studio.bz_soft.newsfeeder.data.models.NewsModel

interface ApiClientInterface {

    suspend fun getCurrentBBCNews(api: String): NewsModel
}