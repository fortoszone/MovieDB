package com.fortoszone.moviedb.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseMovie {
    const val AUTH = "com.fortoszone.moviedb"
    const val SCHEME = "content"

    internal class UserColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "movie"
            const val ID = "id"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTH)
                .appendPath(TABLE_NAME)
                .build()

        }
    }
}