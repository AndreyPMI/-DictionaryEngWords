package com.andreypmi.dictionaryforwords.app.di

import android.content.Context
import android.util.Log
import com.andreypmi.dictionaryforwords.data.repository.WordRepositoryImpl
import com.andreypmi.dictionaryforwords.data.storage.dao.WordDao
import com.andreypmi.dictionaryforwords.data.storage.factory.AppDatabase
import com.andreypmi.dictionaryforwords.domain.repository.WordRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val dataModule = module {
   single<AppDatabase> { AppDatabase.create(get()) }
   factory<WordDao> { get<AppDatabase>().wordDao() }
    single<WordRepository>{ WordRepositoryImpl(get())}
}