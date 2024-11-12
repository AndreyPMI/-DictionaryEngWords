package com.andreypmi.dictionaryforwords.app.di

import com.andreypmi.dictionaryforwords.data.storage.factory.AppDatabase
import org.koin.dsl.module


//val dataModule = module {
//    single { AppDatabase.create(get()) }
//    factory { get<AppDatabase>().wordDao() }
//}