package com.andreypmi.user_feature.userScreen.nested_screens.shared_group.viewmodels

sealed interface ShareGroupIntent {
    object LoadFirstPage : ShareGroupIntent
    object LoadNextPage : ShareGroupIntent
    object ErrorShown : ShareGroupIntent
}