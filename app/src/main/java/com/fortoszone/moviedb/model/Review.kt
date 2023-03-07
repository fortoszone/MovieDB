package com.fortoszone.moviedb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    @SerializedName("id")
    var movieId: String = "",

    @SerializedName("author")
    var author: String = "",

    @SerializedName("avatar_path")
    var avatarPath: String = "",

    @SerializedName("rating")
    val rating: String = "",

    @SerializedName("content")
    var content: String = "",

    @SerializedName("created_at")
    val createdAt: String = ""

) : Parcelable {
    constructor() : this("", "", "", "", "", "")
}