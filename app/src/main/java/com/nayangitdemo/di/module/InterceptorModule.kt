package com.nayangitdemo.di.module

import android.util.Log
import com.nayangitdemo.GithubApplication
import com.nayangitdemo.api.ApiConstant.Companion.HEADER_CACHE_CONTROL
import com.nayangitdemo.api.ApiConstant.Companion.HEADER_PRAGMA
import com.nayangitdemo.di.scope.AppScope
import com.nayangitdemo.helper.ApplicationUtil
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.Interceptor.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit


@Module
class InterceptorModule {

    @Provides
    @AppScope
    fun provideOkHttpClient(
        context: GithubApplication,
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor { provideOfflineCacheInterceptor(context, it) }
            .addNetworkInterceptor { provideCacheInterceptor(context, it) }
            .cache(provideCache(context))
            .build()
    }


    @Provides
    @AppScope
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }


    private fun provideCache(context: GithubApplication): Cache? {
        var cache: Cache? = null
        try {
            cache = Cache(File(context.cacheDir, "http-cache"), 10 * 1024 * 1024)
        } catch (e: Exception) {
            Log.e("Cache", "Could not create Cache!")
        }
        return cache
    }

    private fun provideOfflineCacheInterceptor(
        context: GithubApplication,
        chain: Chain
    ): Response {
        var request = chain.request()

        if (!ApplicationUtil.hasNetwork(context)) {
            val cacheControl = CacheControl.Builder()
                .maxStale(1, TimeUnit.DAYS)
                .build()

            request = request.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .cacheControl(cacheControl)
                .build()
        }

        return chain.proceed(request)
    }

    private fun provideCacheInterceptor(
        context: GithubApplication,
        chain: Chain
    ): Response {
        val response = chain.proceed(chain.request())
        val cacheControl: CacheControl = if (ApplicationUtil.hasNetwork(context)) {
            CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build()
        } else {
            CacheControl.Builder()
                .maxStale(1, TimeUnit.DAYS)
                .build()
        }

        return response.newBuilder()
            .removeHeader(HEADER_PRAGMA)
            .removeHeader(HEADER_CACHE_CONTROL)
            .header(HEADER_CACHE_CONTROL, cacheControl.toString())
            .build()
    }
}