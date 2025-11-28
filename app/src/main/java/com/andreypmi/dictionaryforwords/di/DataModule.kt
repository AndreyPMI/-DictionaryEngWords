package com.andreypmi.dictionaryforwords.di


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.andreypmi.core_domain.repository.NotificationScheduleRepository
import com.andreypmi.core_domain.repository.NotificationSettingsRepository
import com.andreypmi.core_domain.repository.PreferencesDataSource
import com.andreypmi.core_domain.repository.ShareStorageRepository
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.service.QrCodeService
import com.andreypmi.dictionaryforwords.data.api.FirebaseConfig
import com.andreypmi.dictionaryforwords.data.api.http.HttpClientApi
import com.andreypmi.dictionaryforwords.data.api.http.KtorHttpClient
import com.andreypmi.dictionaryforwords.data.api.repository.FirebaseRealtimeRepository
import com.andreypmi.dictionaryforwords.data.bd.repository.NotificationSettingsRepositoryImpl
import com.andreypmi.dictionaryforwords.data.bd.repository.PreferencesDataSourceImpl
import com.andreypmi.dictionaryforwords.data.bd.repository.WordRepositoryImpl
import com.andreypmi.dictionaryforwords.data.bd.storage.dao.CategoriesDao
import com.andreypmi.dictionaryforwords.data.bd.storage.dao.WordDao
import com.andreypmi.dictionaryforwords.data.bd.storage.factory.AppDatabase
import com.andreypmi.dictionaryforwords.data.bd.storage.factory.DatabaseInitializer
import com.andreypmi.qr_generator.QrCodeServiceImpl
import com.andreypmi.workmanager.repository.NotificationScheduleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.assisted.AssistedInject
import javax.inject.Singleton


@Module
interface DataModule {

    @Binds
    @Singleton
    fun bindWordRepository(wordRepositoryImpl: WordRepositoryImpl): WordRepository

    @Binds
    @Singleton
    fun bindNotificationScheduleRepository(notificationScheduleRepositoryImpl: NotificationScheduleRepositoryImpl): NotificationScheduleRepository

    @Binds
    @Singleton
    fun bindNotificationSettingsRepository(notificationSettingsRepositoryImpl: NotificationSettingsRepositoryImpl): NotificationSettingsRepository

    @Binds
    @Singleton
    fun bindPreferencesDataSource(preferencesDataSourceImpl: PreferencesDataSourceImpl): PreferencesDataSource

    @Provides
    @Singleton
    fun providePreferencesDataStore(
        context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("notifications_settings") }
        )
    }

    @Binds
    @Singleton
    fun bindHttpClient(ktorHttpClient: KtorHttpClient): HttpClientApi

    @Binds
    @Singleton
    fun bindShareStorageRepository(firebaseRealtimeRepository: FirebaseRealtimeRepository): ShareStorageRepository

    @Binds
    @Singleton
    fun bindQrCodeService(qrCodeService: QrCodeServiceImpl): QrCodeService

    companion object {

        private const val DATABASE_NAME = "dictionaryWord"

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

        @Provides
        @Singleton
        fun provideKtorHttpClient(): KtorHttpClient {
            return KtorHttpClient()
        }

        @Provides
        @Singleton
        fun provideFirebaseRealtimeRepository(
            httpClientApi: HttpClientApi,
            firebaseConfig: FirebaseConfig
        ): FirebaseRealtimeRepository {
            return FirebaseRealtimeRepository(httpClientApi, firebaseConfig)
        }

        @Provides
        @Singleton
        fun provideQrCodeServiceImpl(): QrCodeServiceImpl {
            return QrCodeServiceImpl()
        }
    }
}