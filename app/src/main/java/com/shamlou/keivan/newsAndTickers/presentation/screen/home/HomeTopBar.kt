package com.shamlou.keivan.newsAndTickers.presentation.screen.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shamlou.keivan.newsAndTickers.R
import com.shamlou.keivan.newsAndTickers.ui.theme.AppContentColor
import com.shamlou.keivan.newsAndTickers.ui.theme.AppThemeColor

@Preview
@Composable
fun HomeTopBar(
) {
    val context = LocalContext.current
    TopAppBar(
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colors.AppContentColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h5
            )
        },
        elevation = 0.dp,
        actions = {

        }
    )
}
