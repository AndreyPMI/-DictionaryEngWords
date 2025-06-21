package com.andreypmi.dictionaryforwords.di


import android.content.Context
import androidx.room.Room
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.dictionaryforwords.data.repository.WordRepositoryImpl
import com.andreypmi.dictionaryforwords.data.storage.dao.CategoriesDao
import com.andreypmi.dictionaryforwords.data.storage.dao.WordDao
import com.andreypmi.dictionaryforwords.data.storage.factory.AppDatabase
import com.andreypmi.dictionaryforwords.data.storage.factory.AppDatabaseCallback
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Lazy
import javax.inject.Singleton


@Module
interface DataModule {

    @Binds
    fun bindNewsRepository(repository: WordRepositoryImpl): WordRepository

    companion object {

        @Provides
        @Singleton
        fun provideAppDatabase(
            context: Context,
            appDatabaseCallback: Lazy<AppDatabaseCallback>
        ): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "dictionary_word"
            )
                .addCallback(appDatabaseCallback.get())
                .fallbackToDestructiveMigration(false)
                .build()
        }

        @Provides
        fun provideWordDao(appDatabase: AppDatabase): WordDao {
            return appDatabase.wordDao()
        }

        @Provides
        fun provideCategoriesDao(appDatabase: AppDatabase): CategoriesDao {
            return appDatabase.categoriesDao()
        }
    }
}