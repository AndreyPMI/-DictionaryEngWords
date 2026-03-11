package com.andreypmi.dictionaryforwords.word_list.presentation.words

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.andreypmi.core_domain.models.Category
import com.andreypmi.dictionaryforwords.core.ui.R
import com.andreypmi.dictionaryforwords.core.ui.theme.DictionaryTheme
import com.andreypmi.dictionaryforwords.word_list.presentation.CardField
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordDialogState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordsUiState
import com.andreypmi.dictionaryforwords.word_list.presentation.words.viewModels.IWordsViewModel.WordsIntent

@Composable
internal fun MainScreen(
    wordsUiState: WordsUiState,
    dialogState: WordDialogState,
    modifier: Modifier = Modifier,
    action: (WordsIntent) -> Unit = {},
) {

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    action(WordsIntent.OpenAddWordDialog)
                }) {
                Icon(
                    painter = painterResource(R.drawable.add_24dp),
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
                    items(wordsUiState.words) { word ->
                        CardField(
                            word = word,
                            onEditClicked = {
                                action(
                                    WordsIntent.OpenEditWordDialog(
                                        word
                                    )
                                )
                            },
                            onDeleteClicked = {
                                action(
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
    )
    when (dialogState) {
        is WordDialogState.Add -> {
            wordsUiState.category?.id?.let {
                DialogWindow(
                    title = stringResource(id = R.string.dialog_add),
                    idCategory = it,
                    onClose = { action(WordsIntent.CloseWordDialog) },
                    onSubmit = { newWord ->
                        action(WordsIntent.AddNewWord(newWord))
                        action(WordsIntent.CloseWordDialog)
                    }
                )
            }
        }

        is WordDialogState.Edit -> {
            val editWord = dialogState.word
            val currentCategory = wordsUiState.category

            if (currentCategory != null) {
                DialogWindow(
                    title = stringResource(id = R.string.dialog_edit),
                    idCategory = currentCategory.id,
                    id = editWord.id,
                    word = editWord.word,
                    translate = editWord.translation,
                    description = editWord.description,
                    onClose = { action(WordsIntent.CloseWordDialog) },
                    onSubmit = { updatedWord ->
                        val fullWord = WordState(
                            id = updatedWord.id,
                            word = updatedWord.word,
                            translation = updatedWord.translation,
                            description = updatedWord.description,
                            idCategory = currentCategory.id,
                        )
                        action(WordsIntent.UpdateWord(fullWord))
                        action(WordsIntent.CloseWordDialog)
                    }
                )
            } else {
                action(WordsIntent.CloseWordDialog)
            }
        }

        is WordDialogState.Hidden -> {
            action(WordsIntent.CloseWordDialog)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview_MainScreen_Content() {
    DictionaryTheme {

        val mockCategory = Category(
            id = "1",
            category = "English"
        )

        val mockWords = listOf(
            WordState(
                id = "1",
                idCategory = "1",
                word = "Hello",
                translation = "Привет",
                description = "Greeting word"
            ),
            WordState(
                id = "2",
                idCategory = "1",
                word = "World",
                translation = "Мир",
                description = "The earth, together with all of its countries"
            ),
            WordState(
                id = "3",
                idCategory = "1",
                word = "Compose",
                translation = "Создавать",
                description = "Modern Android UI toolkit"
            )
        )

        MainScreen(
            wordsUiState = WordsUiState(
                category = mockCategory,
                words = mockWords,
                error = null
            ),
            dialogState = WordDialogState.Hidden,
            action = {}
        )
    }
}