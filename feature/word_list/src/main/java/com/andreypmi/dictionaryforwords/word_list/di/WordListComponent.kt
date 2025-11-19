package com.andreypmi.dictionaryforwords.word_list.di

import com.andreypmi.dictionaryforwords.word_list.presentation.words.viewModels.WordsViewModelFactory
import dagger.Component

@Component(modules = [WordListModule::class], dependencies = [WordListDeps::class])
interface WordListComponent {
    val vmFactory: WordsViewModelFactory

    //    @Component.Factory
//    interface Factory {
//        fun create(
//            @BindsInstance deps: WordListDeps
//        ): WordListComponent
//    }
    @Component.Builder
    interface Builder {
        fun addDeps(deps: WordListDeps): Builder
        fun build(): WordListComponent
    }
}