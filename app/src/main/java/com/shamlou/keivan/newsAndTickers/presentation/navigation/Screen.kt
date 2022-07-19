package com.shamlou.keivan.newsAndTickers.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
}
