package com.example.android.core.data.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by Greta Grigutė on 2018-10-17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
class Url : Parcelable {

    @JsonProperty("type")
    var type: String? = null
    @JsonProperty("url")
    var url: String? = null

    constructor() {}

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.type)
        dest.writeString(this.url)
    }

    protected constructor(`in`: Parcel) {
        this.type = `in`.readString()
        this.url = `in`.readString()
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<Url> = object : Parcelable.Creator<Url> {
            override fun createFromParcel(source: Parcel): Url {
                return Url(source)
            }

            override fun newArray(size: Int): Array<Url?> {
                return arrayOfNulls(size)
            }
        }
    }
}