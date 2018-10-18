package com.example.android.core.data.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.ArrayList

/**
 * Created by Greta Grigutė on 2018-10-16.
 */


@JsonIgnoreProperties(ignoreUnknown = true)
class CharacterComicWrapper {

    @JsonProperty("available")
    var available: Int = 0
    @JsonProperty("collectionURI")
    var collectionUri: String = ""
    @JsonProperty("returned")
    var returned: Int = 0
    @JsonProperty("items")
    var items: List<Comic> = ArrayList<Comic>()
}
