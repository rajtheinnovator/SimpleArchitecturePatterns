package com.example.android.core.data.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.ArrayList

/**
 * Created by Greta Grigutė on 2018-10-17.
 */


@JsonIgnoreProperties(ignoreUnknown = true)
 class Comic: Parcelable {

@JsonProperty("id")
 var id:Int? = null
@JsonProperty("title")
 var title:String? = null
@JsonProperty("description")
 var description:Any? = null
@JsonProperty("startYear")
 var startYear:Int? = null
@JsonProperty("endYear")
 var endYear:Int? = null
@JsonProperty("rating")
 var rating:String? = null
@JsonProperty("type")
 var type:String? = null
@JsonProperty("modified")
 var modified:String? = null
@JsonProperty("name")
 var name:String? = null
@JsonProperty("resourceURI")
 var resourceUri:String? = null
@JsonProperty(value = "thumbnail")
 var thumbnail:Image? = null
@JsonProperty(value = "images")
 var imageList:List<Image>? = null

 val thumbnailUrl:String
get() = if (thumbnail != null) {
thumbnail!!.buildCompleteImagePath()
} else ""

 val imageUrlList: ArrayList<String>
get() {
val results = ArrayList<String>()
if (imageList != null)
{
for (i in imageList!!.indices)
{
results.add(getImageUrl(i))
}
}
return results
}

 constructor() {}

 fun getImageUrl(index:Int):String {
     return if (imageList != null && imageList!!.size >= index) {
         imageList!!.get(index).buildCompleteImagePath()
     } else ""
 }


override fun describeContents():Int {
return 0
}


override fun equals(o:Any?):Boolean {
if (this === o) return true
if (o !is Comic) return false
val comic = o as Comic?
return if (id != null)
id == comic!!.id
else
    comic!!.id == null && if (type != null) type == comic!!.type else comic!!.type == null

}

override fun hashCode():Int {
var result = if (id != null) id!!.hashCode() else 0
result = 31 * result + if (type != null) type!!.hashCode() else 0
return result
}

override fun writeToParcel(dest: Parcel, flags:Int) {
dest.writeValue(this.id)
dest.writeString(this.title)
dest.writeString(this.resourceUri)
dest.writeParcelable(this.thumbnail, flags)
dest.writeTypedList(this.imageList)
}

protected constructor(`in`: Parcel) {
this.id = `in`.readValue(Int::class.java.classLoader) as Int
this.title = `in`.readString()
this.resourceUri = `in`.readString()
this.thumbnail = `in`.readParcelable<Image>(Image::class.java!!.getClassLoader())
this.imageList = `in`.createTypedArrayList(Image.CREATOR)
}

companion object {

 val CREATOR: Parcelable.Creator<Comic> = object: Parcelable.Creator<Comic> {
override fun createFromParcel(source: Parcel):Comic {
return Comic(source)
}

override fun newArray(size:Int):Array<Comic?> {
return arrayOfNulls(size)
}
}
}
}
