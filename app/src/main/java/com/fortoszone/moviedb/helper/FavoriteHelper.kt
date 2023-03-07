import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.fortoszone.moviedb.db.DatabaseMovie.UserColumns.Companion.ID
import com.fortoszone.moviedb.db.DatabaseMovie.UserColumns.Companion.TABLE_NAME
import com.fortoszone.moviedb.helper.DatabaseHelper
import java.sql.SQLException

class FavoriteHelper(context: Context) {
    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: FavoriteHelper? = null
        private lateinit var dataBaseHelper: DatabaseHelper
        lateinit var database: SQLiteDatabase

        fun getInstance(context: Context): FavoriteHelper = INSTANCE ?: synchronized(this) {
            INSTANCE ?: FavoriteHelper(context)
        }
    }

    init {
        dataBaseHelper = DatabaseHelper(context)
    }

    private var INSTANCE: FavoriteHelper? = null
    fun getInstance(context: Context): FavoriteHelper =
        INSTANCE ?: synchronized(this) {
            INSTANCE ?: FavoriteHelper(context)
        }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        open()
        return database.rawQuery(
            "SELECT $ID FROM $DATABASE_TABLE ORDER BY $ID ASC",
            null
        )
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(
            DATABASE_TABLE,
            null,
            values
        )
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(
            DATABASE_TABLE,
            values,
            "$ID = ?",
            arrayOf(id)
        )
    }

    fun deleteById(id: String): Int {
        return database.delete(
            DATABASE_TABLE,
            "$ID = '$id'",
            null
        )
    }
}