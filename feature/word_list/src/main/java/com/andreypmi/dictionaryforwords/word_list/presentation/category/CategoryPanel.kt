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
import com.andreypmi.dictionaryforwords.core.ui.theme.DictionaryTheme
import com.andreypmi.dictionaryforwords.core.ui.theme.LocalIsPreview
import com.andreypmi.dictionaryforwords.core.ui.theme.dimension
import com.andreypmi.dictionaryforwords.word_list.presentation.category.viewModels.ICategoryViewModel
import com.andreypmi.dictionaryforwords.word_list.presentation.models.CategoryState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordDialogState
import com.andreypmi.dictionaryforwords.word_list.presentation.models.WordsUiState
import com.andreypmi.dictionaryforwords.word_list.presentation.words.MainScreen
import com.andreypmi.dictionaryforwords.word_list.presentation.words.viewModels.IWordsViewModel.WordsIntent


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun CategoryPanel(
    wordsState: WordsUiState,
    wordDialogState: WordDialogState,
    onWordIntent: (WordsIntent) -> Unit,
    categoryState: CategoryState,
    categoryDialogState: CategoryDialogState,
    onCategoryIntent: (ICategoryViewModel.CategoryIntent) -> Unit
) {
    val isPreview = LocalIsPreview.current
    var isExpanded by remember { mutableStateOf(!isPreview) }
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
        MainScreen(
            wordsUiState = wordsState,
            dialogState = wordDialogState,
            action = onWordIntent
        )
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
                    state = categoryState,
                    onCategoryClick = { category ->
                        onCategoryIntent(
                            ICategoryViewModel.CategoryIntent.SelectCategory(
                                category
                            )
                        )
                    },
                    onAddCategory = {
                        onCategoryIntent(ICategoryViewModel.CategoryIntent.ShowAddCategoryDialog)
                    },
                    dialogState = categoryDialogState,
                    onEditCategory = {
                        onCategoryIntent(
                            ICategoryViewModel.CategoryIntent.ShowEditCategoryDialog(
                                it
                            )
                        )
                    },
                    onDeleteCategory = {
                        onCategoryIntent(
                            ICategoryViewModel.CategoryIntent.ShowDeleteCategoryDialog(
                                it
                            )
                        )
                    },
                    onDismissDialog = { onCategoryIntent(ICategoryViewModel.CategoryIntent.HideCategoryDialog) },
                    onConfirmAddCategory = {
                        onCategoryIntent(
                            ICategoryViewModel.CategoryIntent.AddCategory(
                                it
                            )
                        )
                    },
                    onConfirmEditCategory = {
                        onCategoryIntent(
                            ICategoryViewModel.CategoryIntent.UpdateCategory(
                                it
                            )
                        )
                    },
                    onConfirmDeleteCategory = {
                        onCategoryIntent(
                            ICategoryViewModel.CategoryIntent.DeleteCategory(
                                it
                            )
                        )
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DictionaryTheme(isPreview = true) {
        CategoryPanel(
            wordsState = WordsUiState(
                category = Category(
                    id = "1",
                    category = "Default"
                ),
                words = emptyList()
            ),
            wordDialogState = WordDialogState.Hidden,
            onWordIntent = {},
            categoryState = CategoryState.Success(listOf(
                Category(
                    id = "1",
                    category = "Default"
                )
            )),
            categoryDialogState = CategoryDialogState.Hidden,
            onCategoryIntent = {}
        )
    }
}
