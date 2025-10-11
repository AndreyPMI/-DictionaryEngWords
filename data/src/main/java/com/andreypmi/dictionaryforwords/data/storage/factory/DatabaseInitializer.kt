package com.andreypmi.dictionaryforwords.data.storage.factory

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.andreypmi.dictionaryforwords.data.storage.entites.CategoriesEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseInitializer @Inject constructor() {
    private var database: AppDatabase? = null

    private val callback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            database?.let { appDatabase ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        appDatabase.categoriesDao().insertCategory(
                            CategoriesEntity(
                                id = UUID.randomUUID().toString(),
                                categoryName = "Default"
                            )
                        )
                        Log.d("DatabaseInitializer", "Default category inserted successfully")
                    } catch (e: Exception) {
                        Log.e("DatabaseInitializer", "Failed to insert default category", e)
                    }
                }
            }
        }
    }

    fun setDatabase(database: AppDatabase) {
        this.database = database
    }

    fun getCallback(): RoomDatabase.Callback = callback
}