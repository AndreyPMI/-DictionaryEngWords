package com.andreypmi.dictionaryforwords.app.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.andreypmi.dictionaryforwords.presentation.features.words.WordsViewModel

val appModule = module {
    viewModel<WordsViewModel>{
        WordsViewModel(
            repository = get()
        )
    }
}
