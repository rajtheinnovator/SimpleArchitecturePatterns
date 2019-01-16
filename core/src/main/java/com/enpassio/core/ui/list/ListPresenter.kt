package com.enpassio.core.ui.list

import android.text.TextUtils
import android.util.Log
import com.enpassio.core.data.DataManager
import com.enpassio.core.data.model.CharacterMarvel
import com.enpassio.core.data.model.DataWrapper
import com.enpassio.core.data.network.RemoteCallback
import com.enpassio.core.ui.base.BasePresenter

class ListPresenter(private val mDataManager: DataManager) : BasePresenter<ListContract.ListView>(), ListContract.ViewActions {

    override fun onInitialListRequested() {
        getCharacters(ITEM_REQUEST_INITIAL_OFFSET, ITEM_REQUEST_LIMIT, "hulk")
    }

    override fun onListEndReached(offset: Int?, limit: Int?, searchQuery: String) {
        getCharacters(offset, limit ?: ITEM_REQUEST_LIMIT, searchQuery)
    }

    override fun onCharacterSearched(searchQuery: String) {
        getCharacters(ITEM_REQUEST_INITIAL_OFFSET, ITEM_REQUEST_LIMIT, searchQuery)
    }

    private fun getCharacters(offset: Int?, limit: Int?, searchQuery: String?) {
        if (!isViewAttached) return
        mView?.showMessageLayout(false)
        mView?.showProgress()
        Log.d("my_tag", "dataManager is: " + mDataManager)
        mDataManager.getCharactersList(offset!!, limit!!, searchQuery!!,
                object : RemoteCallback<DataWrapper<List<CharacterMarvel>>>() {
                    override fun onSuccess(response: DataWrapper<List<CharacterMarvel>>) {
                        if (!isViewAttached) return
                        mView?.hideProgress()
                        val responseResults = response.data?.results
                        Log.d("my_tag", "responseResults size is: " + responseResults?.size)
                        if (responseResults!!.isEmpty()) {
                            mView?.showEmpty()
                            return
                        }

                        if (TextUtils.isEmpty(searchQuery)) {
                            mView?.showCharacters(responseResults!!)
                        } else {
                            mView?.showSearchedCharacters(responseResults!!)
                        }
                    }

                    override fun onUnauthorized() {
                        if (!isViewAttached) return
                        mView?.hideProgress()
                        mView?.showUnauthorizedError()
                    }

                    override fun onFailed(throwable: Throwable) {
                        Log.d("my_tag", "onFailed message is: " + throwable.message)
                        if (!isViewAttached) return
                        mView?.hideProgress()
                        mView?.showError(throwable.message!!)
                    }
                })
    }

    companion object {

        private val ITEM_REQUEST_INITIAL_OFFSET = 0
        private val ITEM_REQUEST_LIMIT = 6
    }
}