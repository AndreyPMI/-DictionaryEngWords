package com.andreypmi.dictionaryforwords.app

import android.app.Application
import android.util.Log
import com.andreypmi.dictionaryforwords.app.di.appModule
import com.andreypmi.dictionaryforwords.app.di.dataModule
import com.andreypmi.dictionaryforwords.app.di.domainModule
import com.andreypmi.dictionaryforwords.data.storage.dao.WordDao
import com.andreypmi.dictionaryforwords.data.storage.entites.WordsEntity
import com.andreypmi.dictionaryforwords.data.storage.factory.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            androidLogger(Level.ERROR)
            modules(listOf(
                dataModule,
                domainModule,
                appModule,
            ))
        }
    }
}