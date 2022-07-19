package com.shamlou.keivan.newsAndTickers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.shamlou.keivan.newsAndTickers.presentation.navigation.NavGraph
import com.shamlou.keivan.newsAndTickers.ui.theme.NewsAndTickersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAndTickersTheme {
                navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}

