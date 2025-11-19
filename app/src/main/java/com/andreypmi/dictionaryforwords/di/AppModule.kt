package com.andreypmi.dictionaryforwords.di

import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.dictionaryforwords.data.repository.WordRepositoryImpl
import dagger.Binds
import dagger.Module


@Module
interface AppModule {
    @Binds
    fun bindNewsRepository(repository: WordRepositoryImpl): WordRepository

}
