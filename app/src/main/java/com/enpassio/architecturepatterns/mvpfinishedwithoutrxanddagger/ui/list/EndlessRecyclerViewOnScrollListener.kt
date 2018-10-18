package com.enpassio.architecturepatterns.mvpfinishedwithoutrxanddagger.ui.list

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 * Created by Greta Grigutė on 2018-10-18.
 */

abstract class EndlessRecyclerViewOnScrollListener : RecyclerView.OnScrollListener {
    private var mCurrentPage = 0
    private var mPreviousTotalItemCount = 0
    private var mLoading = true
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    constructor(layoutManager: LinearLayoutManager) {
        mLayoutManager = layoutManager
    }

    constructor(layoutManager: GridLayoutManager) {
        mLayoutManager = layoutManager
        sVisibleThreshold = sVisibleThreshold * layoutManager.spanCount
    }

    constructor(layoutManager: StaggeredGridLayoutManager) {
        mLayoutManager = layoutManager
        sVisibleThreshold = sVisibleThreshold * layoutManager.spanCount
    }

    fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = mLayoutManager!!.itemCount

        if (mLayoutManager is StaggeredGridLayoutManager) {
            val lastVisibleItemPositions = (mLayoutManager as StaggeredGridLayoutManager)
                    .findLastVisibleItemPositions(null)
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)

        } else if (mLayoutManager is LinearLayoutManager) {
            lastVisibleItemPosition = (mLayoutManager as LinearLayoutManager)
                    .findLastVisibleItemPosition()

        } else if (mLayoutManager is GridLayoutManager) {
            lastVisibleItemPosition = (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
        }


        if (totalItemCount < mPreviousTotalItemCount) { // List was cleared
            mCurrentPage = STARTING_PAGE_INDEX
            mPreviousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                mLoading = true
            }
        }

        /**
         * If it’s still loading, we check to see if the DataSet count has
         * changed, if so we conclude it has finished loading and update the current page
         * number and total item count (+ 1 due to loading view being added).
         */
        if (mLoading && totalItemCount > mPreviousTotalItemCount + 1) {
            mLoading = false
            mPreviousTotalItemCount = totalItemCount
        }

        /**
         * If it isn’t currently loading, we check to see if we have breached
         * + the visibleThreshold and need to reload more data.
         */
        if (!mLoading && lastVisibleItemPosition + sVisibleThreshold > totalItemCount) {
            mCurrentPage++
            onLoadMore(mCurrentPage, totalItemCount)
            mLoading = true
        }
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int)

    companion object {

        private val STARTING_PAGE_INDEX = 0

        /**
         * Low threshold to show the onLoad()/Spinner functionality.
         * If you are going to use this for a production app set a higher value
         * for better UX
         */
        private var sVisibleThreshold = 2
    }
}