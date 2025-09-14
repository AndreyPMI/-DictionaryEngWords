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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andreypmi.core_domain.models.Category
import com.andreypmi.dictionaryforwords.core.ui.R
import com.andreypmi.dictionaryforwords.core.ui.theme.DictionaryTheme
import com.andreypmi.dictionaryforwords.word_list.presentation.CardField
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordDialogState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordsUiState
import com.andreypmi.dictionaryforwords.word_list.presentation.words.viewModels.IWordsViewModel
import com.andreypmi.dictionaryforwords.word_list.presentation.words.viewModels.IWordsViewModel.WordsIntent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
internal fun MainScreen(
    wordsViewModel: IWordsViewModel
) {
    val uiState = wordsViewModel.wordsState.collectAsStateWithLifecycle(
        initialValue = WordsUiState(
            category = Category(0, ""),
            words = emptyList()
        )
    )
    val dialogState by wordsViewModel.wordDialogState.collectAsState()

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
                    uiState.value?.let {
                        items(it.words) { word ->
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
                                },
                                onClickCard = {

                                }
                            )
                        }
                    }
                }
            }
        }
    )
    when (dialogState) {
        is WordDialogState.Add -> {
            uiState.value?.category?.id?.let {
                DialogWindow(
                    title = stringResource(id = R.string.dialog_add),
                    idCategory = it,
                    onClose = { wordsViewModel.handleIntent(WordsIntent.CloseWordDialog) },
                    onSubmit = { newWord ->
                        wordsViewModel.handleIntent(WordsIntent.AddNewWord(newWord))
                        wordsViewModel.handleIntent(WordsIntent.CloseWordDialog)
                    }
                )
            }
        }

        is WordDialogState.Edit -> {
            val editWord = (dialogState as WordDialogState.Edit).word
            val currentCategory = uiState.value?.category

            if ( currentCategory != null) {
                DialogWindow(
                    title = stringResource(id = R.string.dialog_edit),
                    idCategory = currentCategory.id,
                    id = editWord.id,
                    word = editWord.word,
                    translate = editWord.translation,
                    description = editWord.description,
                    onClose = { wordsViewModel.handleIntent(WordsIntent.CloseWordDialog) },
                    onSubmit = { updatedWord ->
                        val fullWord = WordState(
                            id = updatedWord.id,
                            word = updatedWord.word,
                            translation = updatedWord.translation,
                            description = updatedWord.description,
                            idCategory = currentCategory.id,
                        )
                        wordsViewModel.handleIntent(WordsIntent.UpdateWord(fullWord))
                        wordsViewModel.handleIntent(WordsIntent.CloseWordDialog)
                    }
                )
            } else {
                wordsViewModel.handleIntent(WordsIntent.CloseWordDialog)
            }
        }

        is WordDialogState.Hidden -> {
            wordsViewModel.handleIntent(WordsIntent.CloseWordDialog)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    DictionaryTheme {
        MainScreen(object : IWordsViewModel {

            override fun handleIntent(intent: WordsIntent) {
                return
            }

            override val wordsState: StateFlow<WordsUiState> = MutableStateFlow(
                WordsUiState(
                    Category(1, "def"),
                    listOf(WordState(1, 1, "d", "d", "des"))
                )
            ).asStateFlow()
            override val wordDialogState: StateFlow<WordDialogState>
                get() {
                    return mutableStateOf(
                        WordDialogState.Add
                    ) as StateFlow<WordDialogState>
                }


        })
    }
}