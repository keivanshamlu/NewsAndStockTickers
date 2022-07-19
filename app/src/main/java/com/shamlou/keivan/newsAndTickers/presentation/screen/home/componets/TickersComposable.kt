package com.shamlou.keivan.newsAndTickers.presentation.screen.home.componets


import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shamlou.keivan.domain.model.TickerDomain
import com.shamlou.keivan.newsAndTickers.ui.theme.ItemBackgroundColor


@Preview
@Composable
fun TickersComposable(allTickers: List<TickerDomain>) {

    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        allTickers.forEach { ticker ->
            Card(
                modifier = Modifier

                    .padding(start = 8.dp)
                    .height(IntrinsicSize.Min)
                    .width(IntrinsicSize.Min),
                elevation = 4.dp,
                backgroundColor = MaterialTheme.colors.ItemBackgroundColor
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(IntrinsicSize.Max)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ticker.stock.let {
                        Text(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .height(IntrinsicSize.Min)
                                .width(IntrinsicSize.Min),
                            text = it,
                            style = MaterialTheme.typography.body2
                        )
                    }
                    ticker.price.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body2
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}
