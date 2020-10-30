package com.nayangitdemo.ui.fragment

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nayangitdemo.base.BaseFragment
import com.nayangitdemo.base.CustomViewModelFactory
import com.nayangitdemo.common.EventObserver
import com.nayangitdemo.model.RepositoryDetails
import com.nayangitdemo.ui.viewmodel.MainViewModel
import javax.inject.Inject

abstract class AbstractRepoDetailFragment : BaseFragment() {

    companion object {
        const val REPO_DETAILS_URL = "repo_details_url"
        const val REPO_DESCRIPTION = "repo_description"
    }

    @Inject
    lateinit var viewModelFactory: CustomViewModelFactory

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }


    override fun viewInitialization(view: View) {
        getUserNameeNumber()?.let {
            viewModel.fetchGitHubPoplularRepoDetail(it)
            observeDataChange()
        }
    }

    private fun getUserNameeNumber() = arguments?.getString(REPO_DETAILS_URL)


    private fun observeDataChange() {
        viewModel.loadingState.observe(viewLifecycleOwner, Observer { showLoadingState(it) })
        viewModel.apiError.observe(viewLifecycleOwner, EventObserver { handleError(it) })
        viewModel.repoDetailsData.observe(viewLifecycleOwner, EventObserver { setRepoDetailsData(it)})
    }

    abstract fun setRepoDetailsData(repositoryDetails: RepositoryDetails)

}