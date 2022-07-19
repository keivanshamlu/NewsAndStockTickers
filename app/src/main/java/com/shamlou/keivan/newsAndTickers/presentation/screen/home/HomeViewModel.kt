package com.shamlou.keivan.newsAndTickers.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shamlou.keivan.domain.model.TickerDomain
import com.shamlou.keivan.domain.useCase.UseCaseGetTickers
import com.shamlou.keivan.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCaseGetTickers: UseCaseGetTickers
) : ViewModel() {

    // holds state of tickers for example loading, error, success
    // also holds the error data and list of tickers
    private val _tickers = MutableStateFlow<Resource<List<TickerDomain>>>(Resource.loading())
    val tickers: StateFlow<Resource<List<TickerDomain>>>
        get() = _tickers

    // will be called per second to get new tickers
    // viewModelScope for closing things when we are out side of this screen
    fun getTickers() = viewModelScope.launch {

        useCaseGetTickers.invoke(Unit).collect{

            _tickers.tryEmit(it)
        }
    }
}