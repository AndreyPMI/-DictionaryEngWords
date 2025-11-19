package com.andreypmi.dictionaryforwords.di

import com.andreypmi.dictionaryforwords.LoggerImpl
import com.andreypmi.logger.Logger
import dagger.Binds
import dagger.Module


@Module
interface AppModule {
    @Binds
    fun bindLogger(logger: LoggerImpl):Logger

}
