package com.shamlou.keivan.newsAndTickers.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shamlou.keivan.domain.model.news.ResponseNewsDomain
import com.shamlou.keivan.domain.model.tickers.TickerDomain
import com.shamlou.keivan.domain.useCase.UseCaseGetNews
import com.shamlou.keivan.domain.useCase.UseCaseGetTickers
import com.shamlou.keivan.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCaseGetTickers: UseCaseGetTickers,
    private val useCaseGetNews: UseCaseGetNews
) : ViewModel() {

    // holds state of tickers for example loading, error, success
    // also holds the error data and list of tickers
    // holds loading state at first
    // mutable variables are private for avoiding scraping references
    private val _tickers = MutableStateFlow<Resource<List<TickerDomain>>>(Resource.loading())
    val tickers: StateFlow<Resource<List<TickerDomain>>>
        get() = _tickers


    // holds state of news
    private val _news = MutableStateFlow<Resource<ResponseNewsDomain>>(Resource.loading())
    val news: StateFlow<Resource<ResponseNewsDomain>>
        get() = _news

    // top views(first 6 items)
    val topNews = news.map {
        it.data?.articles?.subList(0, 6)
    }

    // bottom views (remaining items)
    val bottomNews = news.map {
        it.data?.articles?.subList(6, it.data?.articles?.size ?: 0)
    }

    init {

        // fetch it one time when app opened
        getNews()
    }

    // will be called per second to get new tickers
    // viewModelScope for closing things when we are out side of this screen
    fun getTickers() = viewModelScope.launch {

        useCaseGetTickers.execute(Unit).collect{

            _tickers.tryEmit(it)
        }
    }

    // get news and set on _new variable
    private fun getNews() = viewModelScope.launch {

        useCaseGetNews.execute(Unit).collect{

            _news.tryEmit(it)
        }
    }
}