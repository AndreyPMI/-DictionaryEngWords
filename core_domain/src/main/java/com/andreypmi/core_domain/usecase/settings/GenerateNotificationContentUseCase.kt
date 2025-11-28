package com.andreypmi.core_domain.usecase.settings

import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.models.settingsModel.NotificationContent
import com.andreypmi.core_domain.models.settingsModel.NotificationContentType
import com.andreypmi.core_domain.models.settingsModel.NotificationSettings
import jakarta.inject.Inject


class GenerateNotificationContentUseCase @Inject constructor() {

    operator fun invoke(settings: NotificationSettings, word: Word): NotificationContent {
        return when (settings.contentType) {
            NotificationContentType.WORD_TRANSLATION -> createWordTranslationContent(word)
            NotificationContentType.WORD_EXAMPLE -> createWordExampleContent(word)
            NotificationContentType.PHRASE_OF_DAY -> createPhraseOfDayContent(word)
            NotificationContentType.MINI_QUIZ -> createMiniQuizContent(word)
        }
    }

    private fun createWordTranslationContent(word: Word): NotificationContent {
        return NotificationContent(
            title = word.word,
            content = word.translate,
            type = NotificationContentType.WORD_TRANSLATION,
            wordId = word.id
        )
    }

    private fun createWordExampleContent(word: Word): NotificationContent {
        val content = word.description ?: word.translate

        return NotificationContent(
            title = word.word,
            content = content,
            type = NotificationContentType.WORD_EXAMPLE,
            wordId = word.id
        )
    }

    private fun createPhraseOfDayContent(word: Word): NotificationContent {
        val content = word.description ?: "${word.word} - ${word.translate}"

        return NotificationContent(
            title = "Фраза дня",
            content = content,
            type = NotificationContentType.PHRASE_OF_DAY,
            wordId = word.id
        )
    }

    private fun createMiniQuizContent(word: Word): NotificationContent {
        return NotificationContent(
            title = "Мини-тест",
            content = "Выбери перевод: ${word.word}",
            type = NotificationContentType.MINI_QUIZ,
            wordId = word.id
        )
    }
}