package studio.bz_soft.newsfeeder.data.http

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import studio.bz_soft.newsfeeder.data.models.NewsModel

interface NewsApiInterface {

    @Deprecated("Due security problems")
    @GET("top-headlines?sources=bbc-news&apiKey={api}")
    fun currentBBCNewsOld(@Path("api") api: String): Call<NewsModel>

    @GET("top-headlines?sources=bbc-news")
    fun currentBBCNews(@Header("X-Api-Key") api: String): Call<NewsModel>
}