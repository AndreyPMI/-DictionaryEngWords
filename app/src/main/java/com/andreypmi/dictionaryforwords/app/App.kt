package com.andreypmi.dictionaryforwords.app

import android.app.Application
import com.andreypmi.dictionaryforwords.di.appModule
import com.andreypmi.dictionaryforwords.di.dataModule
import com.andreypmi.dictionaryforwords.di.domainModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            modules(listOf(
                com.andreypmi.dictionaryforwords.di.appModule,
                com.andreypmi.dictionaryforwords.di.dataModule,
                com.andreypmi.dictionaryforwords.di.domainModule
            ))
        }
    }
}