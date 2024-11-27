package com.andreypmi.dictionaryforwords.domain.usecase

import com.andreypmi.dictionaryforwords.domain.repository.PreferencesRepository

class SaveLastSelectedCategoryUseCase (private val repository: PreferencesRepository) : UseCaseWithParam<String,String> {
    override suspend fun execute(params: String): String {
        TODO("Not yet implemented")
    }
}