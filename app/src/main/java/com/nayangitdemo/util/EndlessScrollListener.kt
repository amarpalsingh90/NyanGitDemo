package com.nayangitdemo.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollListener(layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private val visibleThreshold = 5
    private var firstVisibleItem: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var startPageNumber = 0
    var currentPage = 0
    private var scrollPosition = 0
    private var linearLayoutManager: LinearLayoutManager


    init {
        this.linearLayoutManager = layoutManager
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount

        // End has been reached

        // Do something
        when {
            linearLayoutManager != null -> {
                totalItemCount = linearLayoutManager!!.itemCount
                firstVisibleItem = linearLayoutManager!!.findFirstVisibleItemPosition()
            }

        }

        scrollPosition = recyclerView.computeVerticalScrollOffset()


        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            // End has been reached

            // Do something
            currentPage++

            onLoadMore(currentPage, totalItemCount)

            loading = true
        }
    }

    fun reset() {
        currentPage = startPageNumber
        this.previousTotal = 0
        this.loading = true
    }

    fun setStartPageNumber(startPageNumber: Int, restart: Boolean) {
        this.startPageNumber = startPageNumber
        if (restart)
            reset()
    }

    abstract fun onLoadMore(currentPage: Int, totalItemCount: Int)


    companion object {
        var TAG = EndlessScrollListener::class.java.simpleName
    }
}
