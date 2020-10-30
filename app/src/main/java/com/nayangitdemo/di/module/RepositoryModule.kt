package com.nayangitdemo.di.module

import com.nayangitdemo.api.GithubApi
import com.nayangitdemo.repository.GithubRepository
import com.nayangitdemo.repository.GithubRepositoryImpl
import dagger.Module
import dagger.Provides


@Module
class RepositoryModule {


    @Provides
    fun provideGithubRepository(githubApi: GithubApi): GithubRepository {
        return GithubRepositoryImpl(githubApi)
    }
}