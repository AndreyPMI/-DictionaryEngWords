package com.andreypmi.dictionaryforwords.data.storage.factory

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.andreypmi.dictionaryforwords.data.storage.entites.CategoriesEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseInitializer @Inject constructor() {
    private var database: AppDatabase? = null

    private val callback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("AAA","Callback was started")
            database?.let { appDatabase ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        appDatabase.categoryDao().insertCategory(
                            CategoriesEntity(
                                id_category = 1,
                                category_name = "default"
                            )
                        )
                        Log.d("AAA", "Default category inserted successfully")
                    } catch (e: Exception) {
                        Log.e("AAA", "Failed to insert default category", e)
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