package com.andreypmi.dictionaryforwords.presentation.features.words

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.andreypmi.dictionaryforwords.domain.models.Word
import com.andreypmi.dictionaryforwords.presentation.features.CardField

@Composable
internal fun MainScreen(
    uiState: WordsUiState
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        LazyColumn() {
            items(items = uiState.words) {
                CardField(it)
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
private fun Preview() {
    val item =
        Word(engWord = "engword", ruWord = "ruword", description = "description with long text")

    MainScreen(WordsUiState(words = listOf(item, item, item)))
}