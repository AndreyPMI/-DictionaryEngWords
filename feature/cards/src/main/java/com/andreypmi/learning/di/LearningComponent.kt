package com.andreypmi.learning.di

import com.andreypmi.core_domain.di.DomainComponent
import com.andreypmi.core_domain.di.DomainDeps
import com.andreypmi.learning.categoryScreen.viewModel.CategoriesViewModelFactory
import com.andreypmi.learning.learningScreen.viewModels.LearningSessionViewModel
import com.andreypmi.learning.learningScreen.viewModels.LearningSessionViewModelFactory
import dagger.Component

@Component(dependencies = [LearningDeps::class])
interface LearningComponent {
    val vmCategoryFactory: CategoriesViewModelFactory
    val vmLearningSessionFactory: LearningSessionViewModelFactory
    @Component.Factory
    interface Factory {
        fun create(deps: LearningDeps): LearningComponent
    }
}