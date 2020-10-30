package com.nayangitdemo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nayangitdemo.common.Event
import com.nayangitdemo.common.RxScheduler
import com.nayangitdemo.model.PopularGitRepo
import com.nayangitdemo.model.RepositoryDetails
import com.nayangitdemo.repository.GithubRepository

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val githubRepository: GithubRepository,
    private val scheduler: RxScheduler
) : ViewModel() {

    private val _populargitRepo by lazy { MutableLiveData<Event<PopularGitRepo>>() }
    val populargitRepo: LiveData<Event<PopularGitRepo>> by lazy { _populargitRepo }

    private val _repoDetailsData by lazy { MutableLiveData<Event<RepositoryDetails>>() }
    val repoDetailsData: LiveData<Event<RepositoryDetails>> by lazy { _repoDetailsData }

    var loadingState = MutableLiveData<Boolean>()

    private val _apiError by lazy { MutableLiveData<Event<Throwable>>() }
    val apiError: LiveData<Event<Throwable>> by lazy { _apiError }

    private val disposable by lazy { CompositeDisposable() }


    fun fetchGitHubPoplularRepoList() {
        val issueDisposable = githubRepository.getGitHubPoplularRepoList()
            .subscribeOn(scheduler.io)
            .observeOn(scheduler.main)
            .doOnSubscribe { loadingState.value = true }
            .doOnEvent { _, _ -> loadingState.value = false }
            .doOnError { loadingState.value = false }
            .subscribe(
                { Event(it).run(_populargitRepo::postValue) },
                { Event(it).run(_apiError::postValue) }
            )
        disposable.add(issueDisposable)
    }

    fun fetchGitHubPoplularRepoDetail(endPointURL: String) {
        val commentDisposable = githubRepository.getGitHubPoplularRepoDetail(endPointURL)
            .subscribeOn(scheduler.io)
            .observeOn(scheduler.main)
            .doOnSubscribe { loadingState.value = true }
            .doOnEvent { _, _ -> loadingState.value = false }
            .doOnError { loadingState.value = false }
            .subscribe(
                { Event(it).run(_repoDetailsData::postValue) },
                { Event(it).run(_apiError::postValue) }
            )
        disposable.add(commentDisposable)
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}