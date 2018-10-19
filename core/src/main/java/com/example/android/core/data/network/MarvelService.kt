package com.example.android.core.data.network

import com.example.android.core.data.model.CharacterMarvel
import com.example.android.core.data.model.Comic
import com.example.android.core.data.model.DataWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Greta Grigutė on 2018-10-17.
 */
interface MarvelService {

    /**
     * Retrieve list of characters
     */
    @GET("characters")
    fun getCharacters(@Query("apikey") publicKey: String,
                      @Query("hash") md5Digest: String,
                      @Query("ts") timestamp: Long,
                      @Query("offset") offset: Int?,
                      @Query("limit") limit: Int?,
                      @Query("nameStartsWith") searchQuery: String?): Call<DataWrapper<List<CharacterMarvel>>>

    /**
     * Retrieve character by given Id
     */
    @GET("characters/{characterId}")
    fun getCharacter(@Path("characterId") characterId: Long,
                     @Query("apikey") publicKey: String,
                     @Query("hash") md5Digest: String,
                     @Query("ts") timestamp: Long): Call<DataWrapper<List<CharacterMarvel>>>

    /**
     * Retrieve list of comics by character Id
     */
    @GET("characters/{characterId}/{comicType}")
    fun getCharacterComics(@Path("characterId") characterId: Long,
                           @Path("comicType") comicType: String,
                           @Query("offset") offset: Int?,
                           @Query("limit") limit: Int?,
                           @Query("apikey") publicKey: String,
                           @Query("hash") md5Digest: String,
                           @Query("ts") timestamp: Long): Call<DataWrapper<List<Comic>>>
}