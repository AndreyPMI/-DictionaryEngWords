package com.andreypmi.dictionaryforwords.presentation.features.words

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.andreypmi.dictionaryforwords.core.ui.theme.dialogHeight
import com.andreypmi.dictionaryforwords.core.ui.theme.dialogPadding
import com.andreypmi.dictionaryforwords.core.ui.theme.dialogWidth
import com.andreypmi.dictionaryforwords.core.ui.R
import com.andreypmi.dictionaryforwords.domain.models.Word

const val EMPTY_STRING = ""
@Composable
internal fun DialogWindow(
    title:String,
    idCategory: Int,
    id: Int? = null,
    word:String? = EMPTY_STRING,
    translate:String? = EMPTY_STRING,
    description:String? = EMPTY_STRING,
    onClose: () -> Unit,
    onSubmit: (Word) -> Unit
) {
    val (wordState, setWordState) = remember {
        mutableStateOf(Word(id = id,
            idCategory = idCategory,
            word = word?: EMPTY_STRING,
            translate = translate?: EMPTY_STRING,
            description = description?: EMPTY_STRING))
    }
    Dialog(onDismissRequest = onClose) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(Modifier.padding(dialogPadding)) {
                Text(text = title)
                OutlinedTextField(
                    value = wordState.word,
                    onValueChange = { newWord ->
                        setWordState(wordState.copy(word = newWord))
                    },
                    label = { Text(stringResource(id = R.string.dialog_word)) }
                )
                OutlinedTextField(
                    value = wordState.translate,
                    onValueChange = { newTranslate ->
                        setWordState(wordState.copy(translate = newTranslate))
                    },
                    label = { Text(stringResource(id = R.string.dialog_translation)) }
                )
                OutlinedTextField(
                    value = wordState.description,
                    onValueChange = { newDescription ->
                        setWordState(wordState.copy(description = newDescription))
                    },
                    label = { Text(stringResource(id = R.string.dialog_description)) }
                )
                Spacer(modifier = Modifier.height(dialogHeight))
                Row(horizontalArrangement = Arrangement.End) {
                    Button(onClick = onClose) {
                        Text(stringResource(id = R.string.dialog_cancel))
                    }
                    Spacer(modifier = Modifier.width(dialogWidth))
                    Button(onClick = {
                        onSubmit(wordState)
                    }) {
                        Text(stringResource(id = R.string.dialog_accept))
                    }
                }
            }
        }
    }
}

