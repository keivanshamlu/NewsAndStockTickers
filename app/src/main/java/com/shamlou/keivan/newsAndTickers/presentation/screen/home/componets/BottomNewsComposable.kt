package com.shamlou.keivan.newsAndTickers.presentation.screen.home.componets

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shamlou.keivan.domain.model.tickers.TickerDomain
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shamlou.keivan.domain.model.news.ArticlesDomain
import com.shamlou.keivan.newsAndTickers.presentation.screen.home.HomeViewModel
import com.shamlou.keivan.newsAndTickers.ui.theme.ItemBackgroundColor

@Composable
fun BottomNewsComposable(viewModel: HomeViewModel, news: ArticlesDomain) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.ItemBackgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(news.urlToImage)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
            )
            Column(
                modifier = Modifier
                    .padding(end = 8.dp, start = 8.dp)
                    .fillMaxWidth(),
            ) {
                news.title?.let {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = it,
                        style = MaterialTheme.typography.body1
                    )
                }
                news.content?.let {

                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        text = it,
                        style = MaterialTheme.typography.body2,
                    )
                }
                news.publishedAt?.let {

                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        text = it,
                        style = MaterialTheme.typography.body2,
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
            }

        }
    }

}