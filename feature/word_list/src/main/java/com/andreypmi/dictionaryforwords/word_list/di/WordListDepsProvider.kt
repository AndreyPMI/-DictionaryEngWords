package com.andreypmi.dictionaryforwords.word_list.di

object WordListDepsProvider {

    private var wordListDeps: WordListDeps? = null

    fun initialize(wordListDeps: WordListDeps) {
        this.wordListDeps = wordListDeps
    }

    val deps: WordListDeps by lazy {
        wordListDeps?: throw IllegalArgumentException("no Deps component in word_list")
    }
}