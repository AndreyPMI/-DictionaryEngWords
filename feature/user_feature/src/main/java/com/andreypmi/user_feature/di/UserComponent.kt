package com.andreypmi.user_feature.di

import dagger.Component

@Component(dependencies = [UserDeps::class])
interface UserComponent {
    @Component.Factory
    interface Factory {
        fun create(deps: UserDeps): UserComponent
    }
}