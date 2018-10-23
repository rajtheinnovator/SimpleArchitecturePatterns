package com.enpassio.core.ui.list

import com.enpassio.core.data.model.CharacterMarvel
import com.enpassio.core.ui.base.RemoteView

/**
 * Created by Greta Grigutė on 2018-10-17.
 */
interface ListContract {

    interface ViewActions {
        fun onInitialListRequested()

        // fun onListEndReached(offset: Int, limit: Int, searchQuery: String)

        fun onCharacterSearched(searchQuery: String)
    }

    interface ListView : RemoteView {

        fun showCharacters(characterList: List<CharacterMarvel>)

        fun showSearchedCharacters(characterList: List<CharacterMarvel>)
    }
}