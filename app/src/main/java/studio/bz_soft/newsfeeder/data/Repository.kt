package studio.bz_soft.newsfeeder.data

import studio.bz_soft.mvilibrary.extensions.IO
import studio.bz_soft.mvilibrary.extensions.io
import studio.bz_soft.newsfeeder.data.http.ApiClientInterface
import studio.bz_soft.newsfeeder.data.models.NewsModel
import java.lang.Exception

class Repository(
        private val client: ApiClientInterface
): RepositoryInterface {

    override suspend fun getBBCNews(api: String): IO<NewsModel, Exception> {
        return io { client.getCurrentBBCNews(api) }
    }
}