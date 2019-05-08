package studio.bz_soft.newsfeeder.data.http

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import studio.bz_soft.newsfeeder.data.models.NewsModel
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ApiClient(
    private val apiURL: String,
    private val appContext: Context
) : ApiClientInterface {

    private val retrofitClient by lazy { createRetrofitClient(apiURL) }
    private val apiClient by lazy { retrofitClient.create(NewsApiInterface::class.java) }

    private val cacheSize = (5 * 1024 * 1024).toLong()
    private val httpCache = Cache(appContext.cacheDir, cacheSize)

    private fun createClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(httpCache)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                val request = chain.request()
                chain.proceed(
                    if (hasNetwork(appContext)) {
                        request.newBuilder()
                            .header("Cache-Control", "public, max-age=" + 5)
                            .build()
                    } else {
                        request.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                            .build()
                    }
                )
            }
            .build()
    }

    private fun createRetrofitClient(apiURL: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createClient())
            .build()
    }

    private fun hasNetwork(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return (activeNetwork != null && activeNetwork.isConnected)
    }

    override suspend fun getCurrentBBCNews(api: String): NewsModel {
        return apiClient.currentBBCNews(api).await()
    }

    private suspend inline fun <T> Call<T>.await(): T = suspendCancellableCoroutine { coroutine ->
        enqueue(object : Callback<T> {

            override fun onFailure(call: Call<T>, t: Throwable) {
                coroutine.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                when (val result = response.body()) {
                    null -> when (val error = response.errorBody()) {
                        null -> coroutine.resumeWithException(RuntimeException("Null"))
                        else -> error
                    }
                    else -> coroutine.resume(result)
                }
            }
        })
        coroutine.invokeOnCancellation { cancel() }
    }
}