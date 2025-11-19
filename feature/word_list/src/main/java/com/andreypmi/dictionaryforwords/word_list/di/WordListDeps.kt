package com.andreypmi.dictionaryforwords.word_list.di

import com.andreypmi.core_domain.repository.WordRepository

interface WordListDeps {
    val repository: WordRepository
}