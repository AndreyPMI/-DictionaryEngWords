package com.andreypmi.dictionaryforwords.app

import android.app.Application
import com.andreypmi.core_domain.di.DaggerDomainComponent
import com.andreypmi.core_domain.di.DomainDepsProvider
import com.andreypmi.dictionaryforwords.di.DaggerAppComponent
import com.andreypmi.dictionaryforwords.word_list.di.WordListDepsProvider
import com.andreypmi.learning.di.LearningDepsProvider
import com.andreypmi.user_feature.di.UserDepsProvider

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val appComponent = DaggerAppComponent.factory().create(
            this,
        )
        val domainComponent = DaggerDomainComponent.factory().create(appComponent)
        WordListDepsProvider.initialize(appComponent)
        DomainDepsProvider.initialize(appComponent)
        LearningDepsProvider.initialize(appComponent)
        UserDepsProvider.initialize(appComponent)
    }
}