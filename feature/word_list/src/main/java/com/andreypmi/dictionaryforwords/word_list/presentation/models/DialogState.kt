package com.andreypmi.dictionaryforwords.word_list.presentation.models

import androidx.compose.runtime.Stable
import com.andreypmi.core_domain.models.Word

@Stable
data class DialogState (
    val editWord : Word?,
    val dialogType: DialogType
    )