package com.example.android.core.ui.character

import com.example.android.core.data.DataManager
import com.example.android.core.data.model.CharacterMarvel
import com.example.android.core.data.model.Comic
import com.example.android.core.data.model.DataWrapper
import com.example.android.core.data.network.RemoteCallback
import com.example.android.core.ui.base.BasePresenter

/**
 * Created by Greta Grigutė on 2018-10-17.
 */

class CharacterPresenter(private val mDataManager: DataManager) : BasePresenter<CharacterContract.CharacterView>(), CharacterContract.ViewActions {
    private var mCharacter: CharacterMarvel? = null

   override fun onCharacterRequested(characterId: Long?) {
        getCharacter(characterId!!)
    }
/**
  override fun onCharacterComicsRequested(characterId: Long?, limit: Int) {
        getComicList(characterId!!, null, limit)
    }

    override fun onCharacterSeriesRequested(characterId: Long?, limit: Int) {
        getSeriesList(characterId!!, null, limit)
    }

    override fun onCharacterStoriesRequested(characterId: Long?, limit: Int) {
        getStoriesList(characterId!!, null, limit)
    }

    override fun onCharacterEventsRequested(characterId: Long?, limit: Int) {
        getEventsList(characterId!!, null, limit)
    }
**/
    private fun getCharacter(id: Long) {
        if (!isViewAttached()) return
        mView!!.showMessageLayout(false)
        if (mCharacter != null && mCharacter!!.id!! === id) {
            mView!!.showCharacter(mCharacter!!)
            return
        }

        mView!!.showProgress()
        mDataManager.getCharacter(id, object : RemoteCallback<DataWrapper<List<CharacterMarvel>>>() {
            override fun onSuccess(response: DataWrapper<List<CharacterMarvel>>) {
                if (!isViewAttached()) return
                mView!!.hideProgress()
                if (response.data!!.results!!.isEmpty()) {
                    mView!!.showError("Character does not exist")
                    return
                }
                mCharacter = response.data!!.results!!.get(SINGLE_ITEM_INDEX)
                mView!!.showCharacter(mCharacter!!)
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
/**
    private fun getComicList(id: Long, offset: Int?, limit: Int?) {
        if (!isViewAttached()) return
        mView!!.showMessageLayout(false)
        mView!!.showProgress()

        mDataManager.getComics(id, offset, limit, object : RemoteCallback<DataWrapper<List<Comic>>>() {
           override fun onSuccess(response: DataWrapper<List<Comic>>) {
                if (!isViewAttached()) return
                mView!!.hideProgress()
                if (response.data!!.results!!.isEmpty()) {
                    mView!!.showError("Character has no comics")
                    return
                }
                mView!!.showComicList(response.data!!.results!!)
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

    private fun getSeriesList(id: Long, offset: Int?, limit: Int?) {
        if (!isViewAttached()) return
        mView!!.showMessageLayout(false)
        mView!!.showProgress()

        mDataManager.getSeries(id, offset, limit, object : RemoteCallback<DataWrapper<List<Comic>>>() {
           override fun onSuccess(response: DataWrapper<List<Comic>>) {
                if (!isViewAttached()) return
                mView!!.hideProgress()
                if (response.data!!.results!!.isEmpty()) {
                    mView!!.showError("Character has no series")
                    return
                }
                mView!!.showSeriesList(response.data!!.results!!)
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

    private fun getStoriesList(id: Long, offset: Int?, limit: Int?) {
        if (!isViewAttached()) return
        mView!!.showMessageLayout(false)
        mView!!.showProgress()

        mDataManager.getStories(id, offset, limit, object : RemoteCallback<DataWrapper<List<Comic>>>() {
            override fun onSuccess(response: DataWrapper<List<Comic>>) {
                if (!isViewAttached()) return
                mView!!.hideProgress()
                if (response.data!!.results!!.isEmpty()) {
                    mView!!.showError("Character has no stories")
                    return
                }
                mView!!.showStoriesList(response.data!!.results!!)
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

    private fun getEventsList(id: Long, offset: Int?, limit: Int?) {
        if (!isViewAttached()) return
        mView!!.showMessageLayout(false)
        mView!!.showProgress()

        mDataManager.getEvents(id, offset, limit, object : RemoteCallback<DataWrapper<List<Comic>>>() {
            override fun onSuccess(response: DataWrapper<List<Comic>>) {
                if (!isViewAttached()) return
                mView!!.hideProgress()
                if (response.data!!.results!!.isEmpty()) {
                    mView!!.showError("Character has no events")
                    return
                }
                mView!!.showEventsList(response.data!!.results!!)
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
 **/

    companion object {

        val SINGLE_ITEM_INDEX = 0
    }
}