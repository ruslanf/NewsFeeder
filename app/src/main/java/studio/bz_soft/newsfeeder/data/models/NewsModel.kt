package studio.bz_soft.newsfeeder.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class NewsModel(
        @SerializedName("status") val status: String,
        @SerializedName("totalResults") val total: Int,
        @SerializedName("articles") val listOfArticles: List<Article>
)

@Parcelize
data class Article(
    @SerializedName("source") val source: @RawValue Source,
    @SerializedName("author") val author: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val desc: String,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val image: String,
    @SerializedName("publishedAt") val dateOfPublish: String,
    @SerializedName("content") val content: String
) : Parcelable {
    constructor() : this(Source("", ""), "", "", "",
        "", "", "", "")
}

data class Source(
        @SerializedName("id") val id: String,
        @SerializedName("name") val channelName: String
)
