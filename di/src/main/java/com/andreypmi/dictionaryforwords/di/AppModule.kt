package com.andreypmi.dictionaryforwords.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<WordsViewModel>{
        WordsViewModel(
            repository = get()
        )
    }
}