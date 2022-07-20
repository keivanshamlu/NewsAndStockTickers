package com.shamlou.keivan.domain.useCase

import com.shamlou.keivan.domain.model.news.ResponseNewsDomain
import com.shamlou.keivan.domain.repository.HomeRepository
import com.shamlou.keivan.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class UseCaseGetNews constructor(
    private val repository: HomeRepository
) {

     fun execute(parameters: Unit): Flow<Resource<ResponseNewsDomain>> {
        return repository.getNews()
    }
}

