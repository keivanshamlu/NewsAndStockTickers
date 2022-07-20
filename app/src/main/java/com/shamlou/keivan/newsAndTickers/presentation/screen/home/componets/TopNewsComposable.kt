package com.shamlou.keivan.newsAndTickers.presentation.screen.home.componets
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shamlou.keivan.newsAndTickers.presentation.screen.home.HomeViewModel
import com.shamlou.keivan.newsAndTickers.ui.theme.ItemBackgroundColor

@Composable
fun TopNewsComposable(viewModel: HomeViewModel) {

    val topNews by viewModel.topNews.collectAsState(initial = null)
    val news by viewModel.news.collectAsState(initial = null)

    if(news?.isSuccess() == false)return
    Text(
        modifier = Modifier
            .padding(
                start = 12.dp,
                top = 20.dp
            )
            .fillMaxWidth(),
        text = "Top News",
        style = MaterialTheme.typography.body1
    )
    LazyRow(modifier = Modifier.padding(top = 8.dp)) {

        items(topNews ?: mutableListOf()) { news ->
            Card(
                modifier = Modifier
                    .width(300.dp)
                    .padding(start = 8.dp, end = 8.dp),
                elevation = 4.dp,
                backgroundColor = MaterialTheme.colors.ItemBackgroundColor
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(news.urlToImage)
                            .crossfade(true)
                            .build(),
                        modifier = Modifier.fillMaxWidth(),
                        contentDescription = news.description,
                        contentScale = ContentScale.FillWidth,
                    )
                    news.title?.let {
                        Text(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .fillMaxWidth(),
                            text = it,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

    }

    Text(
        modifier = Modifier
            .padding(
                start = 12.dp,
                top = 20.dp
            )
            .fillMaxWidth(),
        text = "Remaining News",
        style = MaterialTheme.typography.body1
    )
}


