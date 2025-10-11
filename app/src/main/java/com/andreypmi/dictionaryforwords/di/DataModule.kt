package com.andreypmi.dictionaryforwords.di


import android.content.Context
import androidx.room.Room
import com.andreypmi.core_domain.repository.PreferencesDataSource
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.dictionaryforwords.data.repository.PreferencesDataSourceImpl
import com.andreypmi.dictionaryforwords.data.repository.WordRepositoryImpl
import com.andreypmi.dictionaryforwords.data.storage.dao.CategoriesDao
import com.andreypmi.dictionaryforwords.data.storage.dao.WordDao
import com.andreypmi.dictionaryforwords.data.storage.factory.AppDatabase
import com.andreypmi.dictionaryforwords.data.storage.factory.DatabaseInitializer
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
interface DataModule {

    @Binds
    @Singleton
    fun bindWordRepository(wordRepositoryImpl: WordRepositoryImpl): WordRepository

    @Binds
    @Singleton
    fun bindPreferencesDataSource(PreferencesDataSourceImpl: PreferencesDataSourceImpl): PreferencesDataSource

    companion object {

        private const val DATABASE_NAME = "dictionary_word"

        @Provides
        @Singleton
        fun provideAppDatabase(
            context: Context,
            initializer: DatabaseInitializer
        ): AppDatabase {
            val database = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .addCallback(initializer.getCallback())
                .fallbackToDestructiveMigration(true)
                .build()
            initializer.setDatabase(database)

            return database
        }

        @Provides
        @Singleton
        fun provideDatabaseInitializer(): DatabaseInitializer {
            return DatabaseInitializer()
        }

        @Provides
        fun provideWordDao(appDatabase: AppDatabase): WordDao {
            return appDatabase.wordDao()
        }

        @Provides
        fun provideCategoryDao(appDatabase: AppDatabase): CategoriesDao {
            return appDatabase.categoriesDao()
        }
    }
}