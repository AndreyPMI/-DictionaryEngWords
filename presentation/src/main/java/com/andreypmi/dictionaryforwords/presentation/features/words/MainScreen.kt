package com.andreypmi.dictionaryforwords.presentation.features.words

import android.annotation.SuppressLint
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andreypmi.dictionaryforwords.domain.models.Word
import com.andreypmi.dictionaryforwords.presentation.features.CardField
import kotlinx.coroutines.flow.MutableStateFlow
@Composable
internal fun MainScreen(
  wordsViewModel: IWordsViewModel
) {
    val uiState:WordsUiState by wordsViewModel.uiState.collectAsStateWithLifecycle()
    val dialogState by wordsViewModel.dialogState.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick =  wordsViewModel::openAddWordDialog ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
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
                    items(uiState.words) {
                        CardField(it)
                    }
                }
            }
        }
    )
    if (dialogState) {
        DialogWindow(
            onClose = { wordsViewModel.closeAddWordDialog() },
            onSubmit = { newWord -> wordsViewModel.addNewWord(newWord)}
        )
    }
}
