package com.andreypmi.dictionaryforwords.app

import android.app.Application
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {
//    override fun onCreate() {
//        super.onCreate()
//        startKoin{
//            androidLogger()
//            modules(listOf(
//                appModule,
//                dataModule,
//                domainModule
//            ))
//        }
//    }
}