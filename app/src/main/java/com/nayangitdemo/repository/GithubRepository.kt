package com.nayangitdemo.repository


import com.nayangitdemo.model.PopularGitRepo
import com.nayangitdemo.model.RepositoryDetails
import io.reactivex.Single

interface GithubRepository {
    fun getGitHubPoplularRepoList(pageNum:String): Single<PopularGitRepo>
    fun getGitHubPoplularRepoDetail(userName: String): Single<RepositoryDetails>
}