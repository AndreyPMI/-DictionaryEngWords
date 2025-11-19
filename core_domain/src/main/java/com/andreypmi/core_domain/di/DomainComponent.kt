package com.andreypmi.core_domain.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DomainModule::class], dependencies = [DomainDeps::class])
interface DomainComponent {
    @Component.Factory
    interface Factory {
        fun create(deps: DomainDeps): DomainComponent
    }
}