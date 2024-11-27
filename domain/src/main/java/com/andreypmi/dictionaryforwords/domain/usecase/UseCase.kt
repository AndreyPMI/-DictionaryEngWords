package com.andreypmi.dictionaryforwords.domain.usecase

interface UseCaseWithParam<R,P> {
    suspend fun execute(params: P):R
}
interface UseCaseWithoutParam<R>{
    suspend fun execute():R
}