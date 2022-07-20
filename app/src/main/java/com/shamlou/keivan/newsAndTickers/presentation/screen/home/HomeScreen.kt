package com.shamlou.keivan.newsAndTickers.presentation.screen.home


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.shamlou.keivan.newsAndTickers.presentation.screen.home.componets.BottomNewsComposable
import com.shamlou.keivan.newsAndTickers.presentation.screen.home.componets.TickersComposable
import com.shamlou.keivan.newsAndTickers.presentation.screen.home.componets.TickersLoading
import com.shamlou.keivan.newsAndTickers.presentation.screen.home.componets.TopNewsComposable
import com.shamlou.keivan.newsAndTickers.ui.theme.AppContentColor
import com.shamlou.keivan.newsAndTickers.ui.theme.AppThemeColor
import kotlinx.coroutines.delay

@Preview
@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colors.AppThemeColor
    val bottomNews by viewModel.bottomNews.collectAsState(initial = null)
    val news by viewModel.news.collectAsState(initial = null)
    LaunchedEffect(Unit) {
        while (true) {
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
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    TickersComposable(viewModel)
                }

                item {
                    TopNewsComposable(viewModel)
                }

                if(news?.isLoading()==true) {
                    item {
                        TickersLoading()
                    }
                }

                items(bottomNews ?: mutableListOf()) { news ->
                    BottomNewsComposable(viewModel, news)
                }
            }
        }
    )
}

