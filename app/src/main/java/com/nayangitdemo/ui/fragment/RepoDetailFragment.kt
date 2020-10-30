package com.nayangitdemo.ui.fragment

import android.os.Bundle
import android.view.View
import com.nayangitdemo.R
import com.nayangitdemo.model.RepositoryDetails
import kotlinx.android.synthetic.main.fragment_repodetials_layout.*


class RepoDetailFragment : AbstractRepoDetailFragment() {

    companion object {
        val TAG = RepoDetailFragment::class.java.name
        fun getInstance(description: String, userName: String): RepoDetailFragment {
            val fragment = RepoDetailFragment()
            val arg = Bundle()
            arg.putString(REPO_DETAILS_URL, userName)
            arg.putString(REPO_DESCRIPTION, description)
            fragment.arguments = arg
            return fragment
        }
    }


    override fun getLayoutRes() = R.layout.fragment_repodetials_layout

    override fun viewInitialization(view: View) {
        super.viewInitialization(view)
    }

    override fun setRepoDetailsData(repositoryDetails: RepositoryDetails) {

        tvdescription.text = arguments?.getString(REPO_DESCRIPTION)
        tv_repodetials.text = repositoryDetails.toString()
    }

    override fun showLoadingState(loading: Boolean) {
        if (loading)
            progress.visibility = View.VISIBLE
        else
            progress.visibility = View.GONE
    }

    override fun onError(message: String) {
        showToast(message)
    }

}