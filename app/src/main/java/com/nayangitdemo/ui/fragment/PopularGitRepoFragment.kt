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
import com.nayangitdemo.util.EndlessScrollListener
import kotlinx.android.synthetic.main.fragment_pop_repo_layout.*


class PopularGitRepoFragment : AbstractPopRepoFragment(), IAdapterCallback {

    companion object {
        val TAG = PopularGitRepoFragment::class.java.name
        fun newInstance(): PopularGitRepoFragment {
            return PopularGitRepoFragment()
        }
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    private val endlessScrollListener = object : EndlessScrollListener(linearLayoutManager) {
        override fun onLoadMore(currentPage: Int, totalItemCount: Int) {
            getMorePopdata("$currentPage")

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
            layoutManager = linearLayoutManager
            adapter = popularGitRepoAdapter
            addOnScrollListener(endlessScrollListener)
        }
    }

    override fun setPopularGitData(popularGitRepo: PopularGitRepo) {
        popularGitRepoAdapter.swapData(popularGitRepo.items)
    }

    override fun setPopularGitMoreData(popularGitRepo: PopularGitRepo) {
        popularGitRepoAdapter.appendData(popularGitRepo.items)
    }

    override fun showLoadingState(loading: Boolean) {
        if (loading) {
            shimmer_view_container.startShimmerAnimation()
            progress.visibility = View.VISIBLE
        } else {
            shimmer_view_container.stopShimmerAnimation()
            shimmer_view_container.visibility = View.GONE
            progress.visibility = View.GONE
        }
    }

    override fun onError(message: String) {
        showToast(message)
    }


    override fun onRepoDetailClick(descriptio: String, userName: String) {
        fragmentChangeListener.onFragmentChange(
            RepoDetailFragment.getInstance(descriptio, userName),
            RepoDetailFragment.TAG
        )
    }


}