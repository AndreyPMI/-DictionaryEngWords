package com.andreypmi.dictionaryforwords.di

import android.content.Context
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.dictionaryforwords.word_list.di.WordListDeps
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class,])
interface AppComponent: WordListDeps {
    override val repository: WordRepository

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}