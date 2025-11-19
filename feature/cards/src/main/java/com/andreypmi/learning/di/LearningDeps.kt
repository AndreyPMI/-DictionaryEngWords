package com.andreypmi.learning.di

import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.logger.Logger

interface LearningDeps {
    val repository: WordRepository
    val logger : Logger
}