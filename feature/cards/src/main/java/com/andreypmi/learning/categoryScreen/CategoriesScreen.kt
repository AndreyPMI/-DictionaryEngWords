package com.andreypmi.learning.categoryScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.andreypmi.core_domain.models.Category
import com.andreypmi.learning.categoryScreen.models.CategoriesState

@Composable
fun CategoriesScreen(
    categoriesState: CategoriesState,
    onCategoryClick: (String) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            categoriesState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            categoriesState.error != null -> {
                Text(
                    text = categoriesState.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                CategoryGrid(
                    categories = categoriesState.categories,
                    onCategoryClick = { categoryId ->
                        onCategoryClick(categoryId)
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryGrid(
    categories: List<Category>,
    onCategoryClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(16.dp)
    ) {
        items(categories) { category ->
            Card(
                onClick = { onCategoryClick(category.id) },
                modifier = Modifier
                    .padding(8.dp)
                    .height(120.dp)
            ) {
                Text(
                    text = category.category,
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
