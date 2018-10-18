package com.example.android.core.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by Greta Grigutė on 2018-10-17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class DataContainer<T> {

    @JsonProperty("offset")
    var offset: Int? = null
    @JsonProperty("limit")
    var limit: Int? = null
    @JsonProperty("total")
    var total: Int? = null
    @JsonProperty("count")
    var count: Int? = null
    @JsonProperty("results")
    var results: T? = null
}
