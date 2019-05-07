package studio.bz_soft.newsfeeder.data.http

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import studio.bz_soft.newsfeeder.data.models.NewsModel

interface NewsApiInterface {

    @GET("top-headlines?sources=bbc-news&apiKey={api}")
    fun currentBBCNews(@Path("api") api: String): Call<NewsModel>
}