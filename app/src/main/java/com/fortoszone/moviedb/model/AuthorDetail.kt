package com.fortoszone.moviedb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthorDetail(
    @SerializedName("username")
    var author: String = "",

    @SerializedName("avatar_path")
    var avatarPath: String = "",

    @SerializedName("rating")
    val rating: String = "",

) : Parcelable {
    constructor() : this("","")
}