package com.andreypmi.dictionaryforwords.di

import android.content.Context
import com.andreypmi.core_domain.di.DomainDeps
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.dictionaryforwords.word_list.di.WordListDeps
import com.andreypmi.logger.Logger
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class,])
interface AppComponent: WordListDeps,DomainDeps {
    override val repository: WordRepository
    override val logger: Logger

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}