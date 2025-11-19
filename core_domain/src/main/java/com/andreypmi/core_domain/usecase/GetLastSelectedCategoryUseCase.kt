package com.andreypmi.core_domain.usecase

import com.andreypmi.core_domain.repository.PreferencesRepository

class GetLastSelectedCategoryUseCase(private val repository: PreferencesRepository) :
    UseCaseWithParam<String, String> {
    override suspend fun execute(params: String): String {
        TODO("Not yet implemented")
    }
}