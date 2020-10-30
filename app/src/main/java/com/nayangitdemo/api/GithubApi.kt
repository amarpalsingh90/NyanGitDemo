package com.nayangitdemo.api


import com.nayangitdemo.model.PopularGitRepo
import com.nayangitdemo.model.RepositoryDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories?q=language=android&sort=stars")
    fun getGitHubPoplularRepoList(@Query("page") pageNum: String): Single<PopularGitRepo>


    @GET("users/{userName}")
    fun getGitHubPoplularRepoDetail(@Path("userName") userName: String): Single<RepositoryDetails>

}