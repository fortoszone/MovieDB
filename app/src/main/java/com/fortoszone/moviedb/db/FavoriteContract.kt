package com.fort0.githubuserapp.db

import android.provider.BaseColumns

internal class FavoriteContract {
    internal class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "movie"
            val COLUMN_NAME_ID = "id"
        }
    }
}