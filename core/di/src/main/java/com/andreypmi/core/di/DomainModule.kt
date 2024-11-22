package com.andreypmi.dictionaryforwords.app.di

import com.andreypmi.dictionaryforwords.domain.usecase.DeleteWordUseCase
import com.andreypmi.dictionaryforwords.domain.usecase.GetAllWordsUseCase
import com.andreypmi.dictionaryforwords.domain.usecase.GetWordByIdUseCase
import com.andreypmi.dictionaryforwords.domain.usecase.GetWordByNameUseCase
import com.andreypmi.dictionaryforwords.domain.usecase.InsertWordUseCase
import com.andreypmi.dictionaryforwords.domain.usecase.UpdateWordUseCase
import org.koin.dsl.module

//
val domainModule = module {
    factory {  GetAllWordsUseCase(get()) }
    factory {  GetWordByIdUseCase(get()) }
    factory {  GetWordByNameUseCase(get()) }
    factory {  InsertWordUseCase(get()) }
    factory {  UpdateWordUseCase(get()) }
    factory {  DeleteWordUseCase(get()) }
}