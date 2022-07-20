package com.shamlou.keivan.domain.repository

import com.shamlou.keivan.domain.model.news.ResponseNewsDomain
import com.shamlou.keivan.domain.model.tickers.TickerDomain
import com.shamlou.keivan.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getTickers(): Flow<Resource<List<TickerDomain>>>
    fun getNews(): Flow<Resource<ResponseNewsDomain>>
}