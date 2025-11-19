package com.andreypmi.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.andreypmi.navigation_api.BottomNavigationController

class BottomNavigationState : BottomNavigationController {
    var isVisible by mutableStateOf(true)
        private set

    override fun show() {
        isVisible = true
    }

    override fun hide() {
        isVisible = false
    }
}

val LocalBottomNavigationState = compositionLocalOf<BottomNavigationState> {
    error("BottomNavigationState not provided!")
}