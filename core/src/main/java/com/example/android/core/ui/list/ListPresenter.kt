package com.example.android.core.ui.list

import android.text.TextUtils
import com.example.android.core.data.DataManager
import com.example.android.core.data.model.CharacterMarvel
import com.example.android.core.data.model.DataWrapper
import com.example.android.core.data.network.RemoteCallback
import com.example.android.core.ui.base.BasePresenter

/**
 * Created by Greta Grigutė on 2018-10-17.
 */

class ListPresenter(private val mDataManager: DataManager) : BasePresenter<ListContract.ListView>(), ListContract.ViewActions {

    override fun onInitialListRequested() {
        getCharacters(ITEM_REQUEST_INITIAL_OFFSET, ITEM_REQUEST_LIMIT, "hulk")
    }

//   override fun onListEndReached(offset: Int?, limit: Int?, searchQuery: String?) {
//        getCharacters(offset!!, limit ?: ITEM_REQUEST_LIMIT, searchQuery!!)
//    }

    override  fun onCharacterSearched(searchQuery: String) {
        getCharacters(ITEM_REQUEST_INITIAL_OFFSET, ITEM_REQUEST_LIMIT, searchQuery)
    }

    private fun getCharacters(offset: Int, limit: Int, searchQuery: String) {
        if (!isViewAttached()) return
        mView!!.showMessageLayout(false)
        mView!!.showProgress()
        mDataManager.getCharactersList(offset, limit, searchQuery,
                object : RemoteCallback<DataWrapper<List<CharacterMarvel>>>() {
                    override fun onSuccess(response: DataWrapper<List<CharacterMarvel>>) {
                        if (!isViewAttached()) return
                        mView!!.hideProgress()
                        val responseResults = response.data!!.results!!
                        if (responseResults.isEmpty()) {
                            mView!!.showEmpty()
                            return
                        }

                        if (TextUtils.isEmpty(searchQuery)) { mView!!.showCharacters(responseResults)
                        } else {
                            mView!!.showSearchedCharacters(responseResults)
                        }
                    }

                    override fun onUnauthorized() {
                        if (!isViewAttached()) return
                        mView!!.hideProgress()
                        mView!!.showUnauthorizedError()
                    }

                    override fun onFailed(throwable: Throwable) {
                        if (!isViewAttached()) return
                        mView!!.hideProgress()
                        mView!!.showError(throwable.message!!)
                    }
                })
    }

    companion object {

        private val ITEM_REQUEST_INITIAL_OFFSET = 0
        private val ITEM_REQUEST_LIMIT = 6
    }
}