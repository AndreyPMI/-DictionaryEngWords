package com.andreypmi.dictionaryforwords.word_list.presentation.words

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andreypmi.dictionaryforwords.core.ui.R
import com.andreypmi.dictionaryforwords.word_list.presentation.CardField
import com.andreypmi.dictionaryforwords.word_list.presentation.models.DialogType
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordsUiState
import com.andreypmi.dictionaryforwords.word_list.presentation.words.IWordsViewModel.WordsIntent

@Composable
internal fun MainScreen(
    wordsViewModel: IWordsViewModel
) {
    val uiState: WordsUiState by wordsViewModel.uiState.collectAsStateWithLifecycle()
    val dialogState by wordsViewModel.dialogState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    wordsViewModel.handleIntent(WordsIntent.OpenAddWordDialog)
                }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.ic_description_word_plus)
                )
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn {
                    items(uiState.words) { word ->
                        CardField(
                            word = word,
                            onEditClicked = {
                                wordsViewModel.handleIntent(
                                    WordsIntent.OpenEditWordDialog(
                                        word
                                    )
                                )
                            },
                            onDeleteClicked = {
                                wordsViewModel.handleIntent(
                                    WordsIntent.DeleteWord(
                                        word
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    )
    when (dialogState.dialogType) {
        DialogType.ADD -> {
            DialogWindow(
                title = stringResource(id = R.string.dialog_add),
                idCategory = uiState.category.id,
                onClose = { wordsViewModel.handleIntent(WordsIntent.CloseWordDialog) },
                onSubmit = { newWord ->
                    wordsViewModel.handleIntent(WordsIntent.AddNewWord(newWord))
                    wordsViewModel.handleIntent(WordsIntent.CloseWordDialog)
                }
            )
        }

        DialogType.EDIT -> {
            DialogWindow(
                title = stringResource(id = R.string.dialog_edit),
                idCategory = uiState.category.id,
                id = dialogState.editWord?.id,
                word = dialogState.editWord?.word,
                translate = dialogState.editWord?.translate,
                description = dialogState.editWord?.description,
                onClose = { wordsViewModel.handleIntent(WordsIntent.CloseWordDialog) },
                onSubmit = { newWord ->
                    run {
                        wordsViewModel.handleIntent(WordsIntent.UpdateWord(newWord))
                        wordsViewModel.handleIntent(WordsIntent.CloseWordDialog)
                    }
                }
            )
        }

        else -> {
            // No dialog is active
        }
    }
}
