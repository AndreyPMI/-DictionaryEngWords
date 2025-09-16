package com.andreypmi.learning.di

import com.andreypmi.core_domain.di.DomainComponent
import com.andreypmi.core_domain.di.DomainDeps
import com.andreypmi.learning.categoryScreen.viewModel.CategoriesViewModelFactory
import dagger.Component

@Component(dependencies = [LearningDeps::class])
interface LearningComponent {
    val vmCategoryFactory: CategoriesViewModelFactory
    @Component.Factory
    interface Factory {
        fun create(deps: LearningDeps): LearningComponent
    }
}