package com.andreypmi.user_feature.di

import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.logger.Logger

interface UserDeps {
    val repository: WordRepository
    val logger : Logger
}