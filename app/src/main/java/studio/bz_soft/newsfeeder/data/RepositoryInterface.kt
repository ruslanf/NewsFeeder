package studio.bz_soft.newsfeeder.data

interface RepositoryInterface {

    suspend fun getBBCNews(api: String)
}