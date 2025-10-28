package com.andreypmi.user_feature.di

import com.andreypmi.user_feature.userScreen.nested_screens.qrCodeScreen.viewModels.QRCodeViewModelFactory
import com.andreypmi.user_feature.userScreen.nested_screens.shared_group.viewmodels.ShareGroupViewModelFactory
import dagger.Component

@Component(dependencies = [UserDeps::class])
interface UserComponent {
    val vmShareGroupFactory: ShareGroupViewModelFactory
    val vmQRCodeFactory : QRCodeViewModelFactory
    @Component.Factory
    interface Factory {
        fun create(deps: UserDeps): UserComponent
    }
}