package com.andreypmi.dictionaryforwords.app.di

import com.andreypmi.dictionaryforwords.domain.usecase.WordUseCasesFacade
import org.koin.dsl.module
import com.andreypmi.dictionaryforwords.presentation.features.words.WordsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel


val appModule = module {
    factory<WordUseCasesFacade> { WordUseCasesFacade(get()) }
    viewModel<WordsViewModel>{
        WordsViewModel(get())
    }

}
