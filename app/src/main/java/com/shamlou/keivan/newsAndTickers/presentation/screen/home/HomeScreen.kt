package com.shamlou.keivan.newsAndTickers.presentation.screen.home


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.shamlou.keivan.newsAndTickers.presentation.screen.home.componets.TickersComposable
import com.shamlou.keivan.newsAndTickers.presentation.screen.home.componets.TickersLoading
import com.shamlou.keivan.newsAndTickers.ui.theme.AppContentColor
import com.shamlou.keivan.newsAndTickers.ui.theme.AppThemeColor
import kotlinx.coroutines.delay

@Preview
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colors.AppThemeColor
    val tickers by viewModel.tickers.collectAsState()
    LaunchedEffect(Unit) {
        while(true) {
            delay(1000)
            viewModel.getTickers()
        }
    }
    SideEffect {
        systemUiController.setStatusBarColor(
            color = systemBarColor
        )
    }
    Scaffold(
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        contentColor = MaterialTheme.colors.AppContentColor,
        topBar = {
            HomeTopBar()
        },
        content = {
            tickers.data?.let {
                TickersComposable(allTickers = it)
            }
            if (tickers.isLoading()) TickersLoading()
        }
    )
}

