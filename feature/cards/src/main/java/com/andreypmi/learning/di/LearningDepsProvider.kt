package com.andreypmi.learning.di

object LearningDepsProvider {
    private var learningDeps: LearningDeps? = null

    fun initialize(wordListDeps: LearningDeps) {
        this.learningDeps = wordListDeps
    }

    val deps: LearningDeps by lazy {
        learningDeps?: throw IllegalArgumentException("no Deps component in word_list")
    }
}