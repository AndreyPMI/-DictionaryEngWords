package com.andreypmi.dictionaryforwords.data.storage.factory

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andreypmi.dictionaryforwords.data.storage.dao.CategoriesDao
import com.andreypmi.dictionaryforwords.data.storage.dao.WordDao
import com.andreypmi.dictionaryforwords.data.storage.entites.CategoriesEntity
import com.andreypmi.dictionaryforwords.data.storage.entites.WordsEntity

private const val DATABASE_VERSION = 2

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