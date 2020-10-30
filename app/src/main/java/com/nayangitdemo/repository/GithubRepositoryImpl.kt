package com.nayangitdemo.repository


import com.nayangitdemo.api.GithubApi
import com.nayangitdemo.model.PopularGitRepo
import com.nayangitdemo.model.RepositoryDetails
import io.reactivex.Single
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(private val githubApi: GithubApi) :
    GithubRepository {

    override fun getGitHubPoplularRepoList(): Single<PopularGitRepo> {
        return githubApi.getGitHubPoplularRepoList()
    }

    override fun getGitHubPoplularRepoDetail(userName: String): Single<RepositoryDetails> {
        return githubApi.getGitHubPoplularRepoDetail(userName)
    }
}