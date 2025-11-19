package com.andreypmi.user_feature.di

object UserDepsProvider {
    private var userDeps: UserDeps? = null

    fun initialize(wordListDeps: UserDeps) {
        this.userDeps = wordListDeps
    }

    val deps: UserDeps by lazy {
        userDeps?: throw IllegalArgumentException("no Deps component in user_feature")
    }
}