package com.example.android.core.data.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by Greta Grigutė on 2018-10-17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
class Image : Parcelable {

    @JsonProperty(value = "path")
    var path: String? = null
    @JsonProperty(value = "extension")
    var extension: String? = null

    fun buildCompleteImagePath(): String {
        return "$path.$extension"
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(path)
        dest.writeString(extension)
    }

    protected constructor(`in`: Parcel) {
        path = `in`.readString()
        extension = `in`.readString()
    }

    companion object {
@JvmField
        val CREATOR: Parcelable.Creator<Image> = object : Parcelable.Creator<Image> {
            override fun createFromParcel(source: Parcel): Image {
                return Image(source)
            }

            override fun newArray(size: Int): Array<Image?> {
                return arrayOfNulls(size)
            }
        }
    }
}