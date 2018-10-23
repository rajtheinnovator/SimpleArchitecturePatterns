package com.example.android.core.data

import android.util.Log
import com.example.android.core.BuildConfig
import com.example.android.core.data.model.CharacterMarvel
import com.example.android.core.data.model.DataWrapper
import com.example.android.core.data.network.MarvelService
import com.example.android.core.data.network.MarvelServiceFactory
import com.example.android.core.data.network.RemoteCallback
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by Greta Grigutė on 2018-10-09.
 */
/**
 * Api abstraction
 */
class DataManager private constructor() {

    private val mMarvelService: MarvelService

    init {
        mMarvelService = MarvelServiceFactory.makeMarvelService()
    }

    fun getCharactersList(offSet: Int, limit: Int, searchQuery: String,
                          listener: RemoteCallback<DataWrapper<List<CharacterMarvel>>>) {
        val timeStamp = System.currentTimeMillis()
        mMarvelService.getCharacters(BuildConfig.PUBLIC_KEY,
                buildMd5AuthParameter(timeStamp), timeStamp, offSet, limit, searchQuery)
                .enqueue(listener)
    }

    fun getCharacter(characterId: Long, listener: RemoteCallback<DataWrapper<List<CharacterMarvel>>>) {
        val timeStamp = System.currentTimeMillis()
        mMarvelService.getCharacter(characterId, BuildConfig.PUBLIC_KEY,
                buildMd5AuthParameter(timeStamp), timeStamp)
                .enqueue(listener)
    }
    /**

    @StringDef(COMIC_TYPE_COMICS, COMIC_TYPE_SERIES, COMIC_TYPE_STORIES, COMIC_TYPE_EVENTS)
    @Retention(RetentionPolicy.SOURCE)
    private annotation class Type

    fun getComics(characterId: Long, offset: Int?, limit: Int?, listener: RemoteCallback<DataWrapper<List<Comic>>>) {
    getComicListByType(characterId, COMIC_TYPE_COMICS, offset, limit).enqueue(listener)
    }

    fun getSeries(characterId: Long, offset: Int?, limit: Int?, listener: RemoteCallback<DataWrapper<List<Comic>>>) {
    getComicListByType(characterId, COMIC_TYPE_SERIES, offset, limit).enqueue(listener)
    }

    fun getStories(characterId: Long, offset: Int?, limit: Int?, listener: RemoteCallback<DataWrapper<List<Comic>>>) {
    getComicListByType(characterId, COMIC_TYPE_STORIES, offset, limit).enqueue(listener)
    }

    fun getEvents(characterId: Long, offset: Int?, limit: Int?, listener: RemoteCallback<DataWrapper<List<Comic>>>) {
    getComicListByType(characterId, COMIC_TYPE_EVENTS, offset, limit).enqueue(listener)
    }

    /**
     * Base request to prevent code duplication
     *
     * @param id        [CharacterMarvel] Id
     * @param comicType Which [.Type] list should be requested
    */
    private fun getComicListByType(id: Long, @Type comicType: String,
    offset: Int?, limit: Int?): Call<DataWrapper<List<Comic>>> {
    val timeStamp = System.currentTimeMillis()
    return mMarvelService.getCharacterComics(id, comicType, offset, limit, BuildConfig.PUBLIC_KEY,
    buildMd5AuthParameter(timeStamp), timeStamp)
    }
     **/
    companion object {

        private var sInstance: DataManager? = null

        val instance: DataManager
            get() {
                if (sInstance == null) {
                    sInstance = DataManager()
                }
                return sInstance!!
            }


        //      private const val COMIC_TYPE_COMICS = "comics"
        //      private const val COMIC_TYPE_SERIES = "series"
        //      private const val COMIC_TYPE_STORIES = "stories"
        //      private const val COMIC_TYPE_EVENTS = "events"

        /**
         * Builds the required API "hash" parameter (timeStamp + privateKey + publicKey)
         *
         * @param timeStamp Current timeStamp
         * @return MD5 hash string
         */
        private fun buildMd5AuthParameter(timeStamp: Long): String {

            try {
                val md = MessageDigest.getInstance("MD5")
                val messageDigest = md.digest((timeStamp.toString() + BuildConfig.PRIVATE_KEY + BuildConfig.PUBLIC_KEY).toByteArray())
                val number = BigInteger(1, messageDigest)

                var md5 = number.toString(16)
                while (md5.length < 32) {
                    md5 = 0.toString() + md5
                }
                return md5

            } catch (e: NoSuchAlgorithmException) {
                Log.e("DataManager", "Error hashing required parameters: " + e.message)
                return ""
            }

        }
    }
}
