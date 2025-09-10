package com.andreypmi.dictionaryforwords.word_list.presentation.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andreypmi.core_domain.models.Category
import com.andreypmi.dictionaryforwords.core.ui.theme.DictionaryTheme
import com.andreypmi.dictionaryforwords.core.ui.theme.dimension
import com.andreypmi.dictionaryforwords.word_list.presentation.models.CategoryState

@Composable
fun CategoryListContent(
    state: CategoryState,
    onCategoryClick: (Category) -> Unit,
    onAddCategory: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is CategoryState.Loading -> LoadingState()
            is CategoryState.Error -> ErrorState(error = state.message)
            is CategoryState.Empty -> EmptyCategoriesState(onAddCategory)
            is CategoryState.Success -> CategoryList(
                state.categories,
                onCategoryClick,
                onAddCategory
            )
        }
    }
}

@Composable
private fun CategoryList(
    categories: List<Category>,
    onCategoryClick: (Category) -> Unit,
    onAddCategory: () -> Unit = {},
    onDeleteClick: (Category) -> Unit = {},
    onChangeClick: (Category) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(MaterialTheme.dimension.size16)
    ) {
        items(categories, key = { it.id }) { category ->
            CategoryItem(
                category = category,
                onCategoryClick = onCategoryClick,
                onChangeClick = onChangeClick,
                onDeleteClick = onDeleteClick
            )
            Spacer(Modifier.height(MaterialTheme.dimension.size8))
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = onAddCategory,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimension.size16)
        ) {
            Text("Добавить категорию")
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    onCategoryClick: (Category) -> Unit,
    onDeleteClick: (Category) -> Unit = {},
    onChangeClick: (Category) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCategoryClick(category) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(MaterialTheme.dimension.size24)
                    .clip(CircleShape)
            )
            Spacer(Modifier.width(MaterialTheme.dimension.size16))
            Text(
                text = category.category,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Icon(
                modifier = Modifier.clickable { onChangeClick(category) },
                imageVector = Icons.Default.Create,
                contentDescription = "Изменить",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.width(MaterialTheme.dimension.size16))
            Icon(
                modifier = Modifier.clickable { onDeleteClick(category) },
                imageVector = Icons.Default.Delete,
                contentDescription = "Удалить",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DictionaryTheme {
        CategoryListContent(
            state = CategoryState.Success(
                categories = listOf(Category(1, "def"))
            ),
            onCategoryClick = { },
            onAddCategory = {},
        )
    }
}

@Preview
@Composable
private fun PreviewError() {
    DictionaryTheme {
        CategoryListContent(
            state = CategoryState.Error(
                message = "Error"
            ),
            onCategoryClick = { },
            onAddCategory = {},
        )
    }
}

@Preview
@Composable
private fun PreviewIsLoading() {
    DictionaryTheme {
        CategoryListContent(
            state = CategoryState.Loading,
            onCategoryClick = { },
            onAddCategory = {},
        )
    }
}