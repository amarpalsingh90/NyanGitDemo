package com.nayangitdemo.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nayangitdemo.base.BaseFragment
import com.nayangitdemo.base.CustomViewModelFactory
import com.nayangitdemo.common.EventObserver
import com.nayangitdemo.model.PopularGitRepo
import com.nayangitdemo.ui.viewmodel.MainViewModel
import javax.inject.Inject

abstract class AbstractPopRepoFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: CustomViewModelFactory

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchGitHubPoplularRepoList()
    }

    override fun viewInitialization(view: View) {
        observeDataChange()
    }


    private fun observeDataChange() {
        viewModel.loadingState.observe(viewLifecycleOwner, Observer { showLoadingState(it) })
        viewModel.apiError.observe(viewLifecycleOwner, EventObserver { handleError(it) })
        viewModel.populargitRepo.observe(viewLifecycleOwner, EventObserver {
            setPopularGitData(it)
        })
        viewModel.populargitRepoLoadMore.observe(viewLifecycleOwner, EventObserver {
            setPopularGitMoreData(it)
        })


    }


    abstract fun setPopularGitData(popularGitRepo: PopularGitRepo)
    abstract fun setPopularGitMoreData(popularGitRepo: PopularGitRepo)

    protected fun getMorePopdata(pageNum: String) {
        viewModel.fetchGitHubPoplularRepoListMore(pageNum)
    }

}