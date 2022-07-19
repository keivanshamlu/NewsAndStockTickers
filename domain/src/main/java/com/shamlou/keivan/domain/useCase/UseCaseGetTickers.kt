package com.shamlou.keivan.domain.useCase

import com.shamlou.keivan.domain.model.TickerDomain
import com.shamlou.keivan.domain.repository.HomeRepository
import com.shamlou.keivan.domain.util.FlowUseCase
import com.shamlou.keivan.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class UseCaseGetTickers constructor(
    private val repository: HomeRepository
) : FlowUseCase<Unit, List<TickerDomain>> {

    override fun execute(parameters: Unit): Flow<Resource<List<TickerDomain>>> {
        return repository.getTickers()
    }
}