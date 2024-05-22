package com.example.watch.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class SearchResponse {
    @SerializedName("totalResults")
    @Expose
    var totalResults: Int? = null
    @SerializedName("Response")
    @Expose
    var response: Boolean? = false

    @SerializedName("Search")
    @Expose
    var results: List<Movie>? = null
}