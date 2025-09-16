package com.andreypmi.learning.categoryScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andreypmi.core_domain.models.Category
import com.andreypmi.learning.categoryScreen.viewModel.CategoriesIntent
import com.andreypmi.learning.categoryScreen.viewModel.CategoriesViewModel

@Composable
fun CategoriesScreen(
    onCategoryClick: (Int) -> Unit,
    viewModel: CategoriesViewModel
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.processIntent(CategoriesIntent.LoadCategories)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            state.error != null -> {
                Text(
                    text = state.error!!,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                CategoryGrid(
                    categories = state.categories,
                    onCategoryClick = { categoryId ->
                        viewModel.processIntent(CategoriesIntent.CategoryClicked(categoryId))
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
    onCategoryClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        items(categories) { category ->
            Card(
                onClick = { onCategoryClick(category.id) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = category.category,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
