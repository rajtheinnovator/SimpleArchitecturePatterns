package com.enpassio.core.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*


@JsonIgnoreProperties(ignoreUnknown = true)
class CharacterComicWrapper {

    @JsonProperty("available")
    var available: Int = 0
    @JsonProperty("collectionURI")
    var collectionUri: String = ""
    @JsonProperty("returned")
    var returned: Int = 0
    @JsonProperty("items")
    var items: List<Comic> = ArrayList()
}
