package com.example.android.core.ui.character

import com.example.android.core.data.model.CharacterMarvel
import com.example.android.core.data.model.Comic
import com.example.android.core.ui.base.RemoteView

/**
 * Created by Greta Grigutė on 2018-10-17.
 */

interface CharacterContract {

    interface ViewActions {

        fun onCharacterRequested(characterId: Long?)

  //      fun onCharacterComicsRequested(characterId: Long?, limit: Int)

  //      fun onCharacterSeriesRequested(characterId: Long?, limit: Int)

  //      fun onCharacterStoriesRequested(characterId: Long?, limit: Int)

  //      fun onCharacterEventsRequested(characterId: Long?, limit: Int)
    }

    interface CharacterView : RemoteView {

        fun showCharacter(character: CharacterMarvel)

 //       fun showComicList(comicList: List<Comic>)

 //       fun showSeriesList(seriesList: List<Comic>)

 //       fun showStoriesList(storiesList: List<Comic>)

 //       fun showEventsList(eventsList: List<Comic>)
    }
}
