package com.fortoszone.moviedb.model.local.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
    @SerializedName("id")
    var id: String = "",

    @SerializedName("title")
    var title: String = "",

    @SerializedName("release_date")
    var releaseDate: String = "",

    @SerializedName("poster_path")
    var posterPath: String = "",

    @SerializedName("overview")
    var overview: String = "",

    @SerializedName("backdrop_path")
    var backdrop: String = ""
) : Parcelable {
    constructor() : this("", "", "", "", "", "")
}