package com.andreypmi.dictionaryforwords.presentation.features.words

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andreypmi.dictionaryforwords.domain.models.Word
import com.andreypmi.dictionaryforwords.presentation.features.CardField
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
internal fun MainScreen(
    wordsViewModel: IWordsViewModel = viewModel()
) {
    val uiState:WordsUiState by wordsViewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        LazyColumn {
            items(items = uiState.words) {
                CardField(it)
            }
        }
    }
}
// Фиктивная реализация WordsViewModel
class FakeWordsViewModel(words:List<Word>) : IWordsViewModel {
    override val uiState = MutableStateFlow(WordsUiState(words))
}

// Предпросмотр
@Preview(showSystemUi = true)
@Composable
private fun Preview() {

    // Генерация списка слов
    val mockedWords = listOf(
        Word(
            id = 1,
            word = "apple",
            translate = "яблоко",
            description = "Фрукт"
        ),
        Word(
            id = 2,
            word = "banana",
            translate = "банан",
            description = "Тропический фрукт"
        ),
        Word(
            id = 3,
            word = "orange",
            translate = "апельсин",
            description = "Цитрусовый фрукт"
        )
    )
    val mockedViewModel = FakeWordsViewModel(mockedWords)
    // Вызов MainScreen с моковым ViewModel
    MainScreen(wordsViewModel = mockedViewModel)
}