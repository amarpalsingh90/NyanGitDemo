package com.nayangitdemo.di.module

import com.nayangitdemo.api.GithubApi
import com.nayangitdemo.common.BaseSchedulerProvider
import com.nayangitdemo.common.RxScheduler
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module(includes = [InterceptorModule::class, RepositoryModule::class])
class ApiModule {

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(com.nayangitdemo.api.ApiConstant.GITHUB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideApiInterface(retrofit: Retrofit): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }

    @Provides
    fun provideRxScheduler(): RxScheduler {
        return BaseSchedulerProvider()
    }
}