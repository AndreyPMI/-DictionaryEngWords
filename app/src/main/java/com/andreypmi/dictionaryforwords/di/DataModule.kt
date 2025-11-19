package com.andreypmi.dictionaryforwords.di


import android.content.Context
import androidx.room.Room
import com.andreypmi.dictionaryforwords.data.storage.dao.WordDao
import com.andreypmi.dictionaryforwords.data.storage.factory.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
interface DataModule {
    companion object {

        @Provides
        @Singleton
        fun provideAppDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "dictionary_word"
            )
                .fallbackToDestructiveMigration(false)
                .build()
        }

        @Provides
        fun provideWordDao(appDatabase: AppDatabase): WordDao {
            return appDatabase.wordDao()
        }
    }
}