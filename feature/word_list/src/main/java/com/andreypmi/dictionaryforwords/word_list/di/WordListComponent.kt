package com.andreypmi.dictionaryforwords.word_list.di

import com.andreypmi.dictionaryforwords.word_list.presentation.category.viewModels.CategoryViewModelFactory
import com.andreypmi.dictionaryforwords.word_list.presentation.words.viewModels.WordsViewModelFactory
import dagger.Component

@Component(modules = [WordListModule::class], dependencies = [WordListDeps::class])
interface WordListComponent {
    val vmWordsFactory: WordsViewModelFactory
    val vmCategoryFactory: CategoryViewModelFactory

    @Component.Builder
    interface Builder {
        fun addDeps(deps: WordListDeps): Builder
        fun build(): WordListComponent
    }
}