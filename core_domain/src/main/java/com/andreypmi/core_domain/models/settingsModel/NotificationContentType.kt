package com.andreypmi.core_domain.models.settingsModel

enum class NotificationContentType {
    WORD_TRANSLATION,
    WORD_EXAMPLE,
    PHRASE_OF_DAY,
    MINI_QUIZ;

    val displayName: String
        get() = when (this) {
            WORD_TRANSLATION -> "Слово + перевод"
            WORD_EXAMPLE -> "Слово + пример"
            PHRASE_OF_DAY -> "Фраза дня"
            MINI_QUIZ -> "Мини-тест"
        }

    companion object {
        fun fromName(name: String): NotificationContentType {
            return try {
                valueOf(name)
            } catch (e: IllegalArgumentException) {
                WORD_TRANSLATION
            }
        }
    }
}