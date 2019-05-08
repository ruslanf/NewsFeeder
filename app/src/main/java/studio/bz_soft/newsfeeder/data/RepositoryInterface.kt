package studio.bz_soft.newsfeeder.data

import studio.bz_soft.mvilibrary.extensions.IO
import studio.bz_soft.newsfeeder.data.models.NewsModel
import java.lang.Exception

interface RepositoryInterface {

    suspend fun getBBCNews(api: String): IO<NewsModel, Exception>
}