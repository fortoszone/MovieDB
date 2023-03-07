package com.fortoszone.moviedb.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.fort0.githubuserapp.db.FavoriteContract

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Favorite.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${FavoriteContract.FavoriteColumns.TABLE_NAME} " + "(${FavoriteContract.FavoriteColumns.COLUMN_NAME_ID} TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FavoriteContract.FavoriteColumns.TABLE_NAME}"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}