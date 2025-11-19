package com.andreypmi.dictionaryforwords.app

import android.app.Application
import com.andreypmi.dictionaryforwords.di.DaggerAppComponent
import com.andreypmi.dictionaryforwords.word_list.di.WordListDepsProvider

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val appComponent = DaggerAppComponent.factory().create(
            this,
        )
        WordListDepsProvider.initialize(appComponent)
    }
}