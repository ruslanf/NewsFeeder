package studio.bz_soft.newsfeeder.data.models

import com.google.gson.annotations.SerializedName

data class NewsModel(
        @SerializedName("status") val status: String,
        @SerializedName("totalResults") val total: Int,
        @SerializedName("articles") val listOfArticles: List<Article>
)

data class Article(
        @SerializedName("source") val source: Source,
        @SerializedName("author") val author: String,
        @SerializedName("title") val title: String,
        @SerializedName("description") val desc: String,
        @SerializedName("url") val url: String,
        @SerializedName("urlToImage") val image: String,
        @SerializedName("publishedAt") val dateOfPublish: String,
        @SerializedName("content") val content: String
)

data class Source(
        @SerializedName("id") val id: String,
        @SerializedName("name") val channelName: String
)
