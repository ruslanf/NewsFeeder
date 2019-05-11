package studio.bz_soft.newsfeeder.data.http

import okhttp3.CacheControl
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit

fun cacheInterceptor(): Interceptor = Interceptor { chain ->
    var request = chain.request()
    val originalResponse = chain.proceed(request)
    val cacheControl = originalResponse.header("Cache-Control")

    if (cacheControl == null ||
        cacheControl.contains("no-store") ||
        cacheControl.contains("no-cache") ||
        cacheControl.contains("must-revalidate") ||
        cacheControl.contains("max-stale = 60 * 60 * 24 * 7")
    ) {
        val cc = CacheControl.Builder()
            .maxStale(1, TimeUnit.DAYS)
            .build()

        request = request.newBuilder()
            .removeHeader("Pragma")
            .cacheControl(cc)
            .build()
        chain.proceed(request)
    } else {
        originalResponse
    }
}

fun offlineCacheInterceptor(): Interceptor = Interceptor { chain ->
    val request = chain.request()
    try {
        chain.proceed(request)
    } catch (ex: Exception) {
        val cacheControl = CacheControl.Builder()
            .onlyIfCached()
            .maxStale(1, TimeUnit.DAYS)
            .build()

        val offlineRequest = request.newBuilder()
            .removeHeader("Pragma")
            .cacheControl(cacheControl)
            .build()
        chain.proceed(offlineRequest)
    }
}
