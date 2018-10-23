package com.example.android.core.data.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

/**
 * Model for Marvel character
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class CharacterMarvel : Parcelable {

    /**
     * END of custom methods
     */

    @JsonProperty(value = "id")
    var id: Long = 0
    @JsonProperty(value = "name")
    var name: String? = null
    @JsonProperty(value = "description")
    var description: String? = null
    @JsonProperty(value = "thumbnail")
    var thumbnail: Image? = null
    @JsonProperty(value = "comics")
    var comics: CharacterComicWrapper? = null
    @JsonProperty(value = "series")
    var series: CharacterComicWrapper? = null
    @JsonProperty(value = "stories")
    var stories: CharacterComicWrapper? = null
    @JsonProperty(value = "events")
    var events: CharacterComicWrapper? = null
    @JsonProperty(value = "urls")
    var urls: List<Url>? = null

    /**
     * START of custom methods
     */

    val imageUrl: String
        get() = thumbnail!!.buildCompleteImagePath()

    constructor() {}

    override fun toString(): String {
        return name!!
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is CharacterMarvel) return false
        val that = o as CharacterMarvel?
        return id == that!!.id
    }

    override fun hashCode(): Int {
        return (id xor id.ushr(32)).toInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeString(name)
        dest.writeString(description)
        dest.writeParcelable(thumbnail, flags)
        dest.writeList(urls)
    }

    protected constructor(`in`: Parcel) {
        id = `in`.readLong()
        name = `in`.readString()
        description = `in`.readString()
        thumbnail = `in`.readParcelable<Image>(Image::class.java!!.getClassLoader())
        urls = ArrayList<Url>()
        `in`.readList(urls, Url::class.java!!.getClassLoader())
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CharacterMarvel> = object : Parcelable.Creator<CharacterMarvel> {
            override fun createFromParcel(source: Parcel): CharacterMarvel {
                return CharacterMarvel(source)
            }

            override fun newArray(size: Int): Array<CharacterMarvel?> {
                return arrayOfNulls(size)
            }
        }
    }
}
