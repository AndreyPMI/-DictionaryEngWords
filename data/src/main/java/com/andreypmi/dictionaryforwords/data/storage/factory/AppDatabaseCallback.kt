package com.andreypmi.dictionaryforwords.data.storage.factory

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.andreypmi.dictionaryforwords.data.storage.entites.CategoriesEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.Lazy

class AppDatabaseCallback @Inject constructor(
   private val appDBLazy: Lazy<AppDatabase>
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            val appDB = appDBLazy.get()
            appDB.categoriesDao().insertCategory(
                CategoriesEntity(
                    id_category = 1,
                    category_name = "Default"
                )
            )
        }
    }
}