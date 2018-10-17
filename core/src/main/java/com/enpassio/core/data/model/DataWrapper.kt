package com.enpassio.core.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
class DataWrapper<T> {

    @JsonProperty("code")
    var code: Int = 0
    @JsonProperty("status")
    var status: String = ""
    @JsonProperty("copyright")
    var copyright: String = ""
    @JsonProperty("attributionText")
    var attributionText: String = ""
    @JsonProperty("attributionHTML")
    var attributionHTML: String = ""
    @JsonProperty("etag")
    var eTag: String = ""
    @JsonProperty("data")
    var data: DataContainer<T>? = null
}
