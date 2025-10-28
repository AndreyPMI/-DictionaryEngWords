package com.andreypmi.dictionaryforwords.data.bd.storage.factory

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andreypmi.dictionaryforwords.data.bd.storage.dao.CategoriesDao
import com.andreypmi.dictionaryforwords.data.bd.storage.dao.WordDao
import com.andreypmi.dictionaryforwords.data.bd.storage.entites.CategoriesEntity
import com.andreypmi.dictionaryforwords.data.bd.storage.entites.WordsEntity

private const val DATABASE_VERSION = 1

@Database(
    entities = [
        CategoriesEntity::class,
        WordsEntity::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
    abstract fun categoriesDao(): CategoriesDao
}