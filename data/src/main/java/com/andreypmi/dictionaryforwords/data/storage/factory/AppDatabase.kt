package com.andreypmi.dictionaryforwords.data.storage.factory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andreypmi.dictionaryforwords.data.storage.dao.WordDao
import com.andreypmi.dictionaryforwords.data.storage.entites.CategoriesEntity
import com.andreypmi.dictionaryforwords.data.storage.entites.WordsEntity

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "dictionary.db"

@Database(
    entities = [
        CategoriesEntity::class,
        WordsEntity::class
    ],
    version = DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao():WordDao
    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context = context, klass = AppDatabase::class.java, name = DATABASE_NAME
            ).createFromAsset(DATABASE_NAME).fallbackToDestructiveMigration().build()
        }
    }
}