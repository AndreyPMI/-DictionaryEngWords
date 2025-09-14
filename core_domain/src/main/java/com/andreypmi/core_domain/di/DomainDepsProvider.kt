package com.andreypmi.core_domain.di

object DomainDepsProvider {
    private var domainDeps: DomainDeps? = null

    fun initialize(domainDeps: DomainDeps) {
        this.domainDeps = domainDeps
    }

    val deps: DomainDeps by lazy {
        domainDeps?: throw IllegalArgumentException("no Deps component in domain")
    }
}