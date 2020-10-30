package com.nayangitdemo.api


import com.nayangitdemo.model.PopularGitRepo
import com.nayangitdemo.model.RepositoryDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("search/repositories?q=language=android&sort=stars&page=1")
    fun getGitHubPoplularRepoList(): Single<PopularGitRepo>


    @GET("users/{userName}")
    fun getGitHubPoplularRepoDetail(@Path("userName") userName: String): Single<RepositoryDetails>

}