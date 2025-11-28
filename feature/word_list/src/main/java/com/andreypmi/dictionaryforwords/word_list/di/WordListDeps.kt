package com.andreypmi.dictionaryforwords.word_list.di

import com.andreypmi.core_domain.repository.NotificationScheduleRepository
import com.andreypmi.core_domain.repository.NotificationSettingsRepository
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.logger.Logger

interface WordListDeps {
    val wordRepository: WordRepository
    val logger : Logger
}