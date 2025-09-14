package com.andreypmi.dictionaryforwords.word_list.di

import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.logger.Logger

interface WordListDeps {
    val repository: WordRepository
    val logger : Logger
}