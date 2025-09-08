package com.andreypmi.dictionaryforwords.word_list.presentation.category

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.models.Word
import com.andreypmi.dictionaryforwords.core.ui.theme.DictionaryTheme
import com.andreypmi.dictionaryforwords.core.ui.theme.dimension
import com.andreypmi.dictionaryforwords.word_list.presentation.models.CategoryState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.DialogState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.DialogType
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordsUiState
import com.andreypmi.dictionaryforwords.word_list.presentation.words.IWordsViewModel
import com.andreypmi.dictionaryforwords.word_list.presentation.words.MainScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun CategoryPanel(wordsViewModel: IWordsViewModel) {
    var isExpanded by remember { mutableStateOf(true) }
    val size32 = MaterialTheme.dimension.size32
    val panelWidth = MaterialTheme.dimension.size112
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val panelOffsetX by animateDpAsState(
        targetValue = if (isExpanded) screenWidth - size32 else 0.dp,
        label = "panelSlide"
    )

    Box(Modifier.fillMaxSize()) {
        MainScreen(wordsViewModel)
        if (!isExpanded) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable { isExpanded = true }
            )
        }

        Row(
            modifier = Modifier
                .offset { IntOffset(panelOffsetX.roundToPx(), 0) }
                .fillMaxHeight(),
            verticalAlignment = Alignment.Top
        ) {
            CategoryBookmark(
                isExpanded = isExpanded,
                onToggle = { isExpanded = !isExpanded },
                modifier = Modifier.padding(top = panelWidth)
            )

            Surface(
                modifier = Modifier
                    .offset { IntOffset(0, (panelWidth / 2).roundToPx()) }
                    .width(screenWidth - size32)
                    .heightIn(max = screenHeight * 0.75f),
                tonalElevation = MaterialTheme.dimension.size8
            ) {
                CategoryListContent(
                    state = CategoryState.Empty,
                    onCategoryClick = { },
                    onAddCategory = {}
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DictionaryTheme {
        CategoryPanel(
            object : IWordsViewModel {

                override fun handleIntent(intent: IWordsViewModel.WordsIntent) {
                    return
                }

                override val uiState: StateFlow<WordsUiState> = MutableStateFlow(
                    WordsUiState(
                        Category(1, "def"),
                        listOf(Word(1, 1, "d", "d", "des"))
                    )
                ).asStateFlow()
                override val dialogState: StateFlow<DialogState> = MutableStateFlow(
                    DialogState(
                        editWord = null,
                        dialogType = DialogType.NONE
                    )
                )
            })
    }
}
