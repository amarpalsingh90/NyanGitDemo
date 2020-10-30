package com.nayangitdemo.ui.fragment

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.nayangitdemo.R
import com.nayangitdemo.callback.IAdapterCallback
import com.nayangitdemo.callback.IFragmentChangeCallback
import com.nayangitdemo.model.Item
import com.nayangitdemo.model.PopularGitRepo
import com.nayangitdemo.ui.adapter.PopularGitRepoAdapter
import com.nayangitdemo.ui.fragment.AbstractRepoDetailFragment.Companion.REPO_DESCRIPTION
import kotlinx.android.synthetic.main.fragment_pop_repo_layout.*


class PopularGitRepoFragment : AbstractPopRepoFragment(), IAdapterCallback {

    companion object {
        val TAG = PopularGitRepoFragment::class.java.name
        fun newInstance(): PopularGitRepoFragment {
            return PopularGitRepoFragment()
        }
    }

    private lateinit var fragmentChangeListener: IFragmentChangeCallback
    private val popularGitRepoList by lazy { ArrayList<Item>() }
    private val popularGitRepoAdapter by lazy {
        PopularGitRepoAdapter(popularGitRepoList, this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentChangeListener = context as IFragmentChangeCallback
    }

    override fun getLayoutRes() = R.layout.fragment_pop_repo_layout

    override fun viewInitialization(view: View) {
        super.viewInitialization(view)
        initAdapter()
    }

    private fun initAdapter() {
        with(parent_recycler) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = popularGitRepoAdapter
        }
    }

    override fun setPopularGitData(popularGitRepo: PopularGitRepo) {
        popularGitRepoList.addAll(popularGitRepo.items)
        with(popularGitRepoAdapter) {
            notifyDataSetChanged()
        }
    }

    override fun showLoadingState(loading: Boolean) {
        if (loading)
            shimmer_view_container.startShimmerAnimation()
        else {
            shimmer_view_container.stopShimmerAnimation()
            shimmer_view_container.visibility = View.GONE
        }
    }

    override fun onError(message: String) {
        showToast(message)
    }


    override fun onRepoDetailClick(descriptio:String,userName: String) {
        fragmentChangeListener.onFragmentChange(
            RepoDetailFragment.getInstance(descriptio,userName),
            RepoDetailFragment.TAG
        )
    }

}