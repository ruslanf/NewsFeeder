package studio.bz_soft.newsfeeder.data

import studio.bz_soft.newsfeeder.data.http.ApiClientInterface

class Repository(
        private val client: ApiClientInterface
): RepositoryInterface {

    override suspend fun getBBCNews(api: String) {
        client.getCurrentBBCNews(api)
    }
}