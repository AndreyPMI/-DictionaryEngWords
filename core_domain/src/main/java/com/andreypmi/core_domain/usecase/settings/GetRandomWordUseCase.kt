package com.andreypmi.core_domain.usecase.settings

import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.repository.NotificationSettingsRepository
import com.andreypmi.core_domain.repository.WordRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first

class GetRandomWordUseCase @Inject constructor(
    private val wordsRepository: WordRepository,
    private val settingsRepository: NotificationSettingsRepository
)  {
    suspend operator fun invoke(): Word {
        val settings = settingsRepository.getSettings()
        val words = wordsRepository.getWordsByCategoryId(settings.selectedCategoryId ?: "").first()

        if (words.isEmpty()) {
            throw IllegalStateException("No words available in selected category")
        }

        val nextIndex = getNextWordIndex(settings.selectedCategoryId, words.size)

        return words[nextIndex]
    }

    private suspend fun getNextWordIndex(categoryId: String?, totalWords: Int): Int {
        val lastIndex = settingsRepository.getLastShownWordIndex(categoryId) ?: -1
        val nextIndex = (lastIndex + 1) % totalWords

        settingsRepository.saveLastShownWordIndex(categoryId, nextIndex)

        return nextIndex
    }
}