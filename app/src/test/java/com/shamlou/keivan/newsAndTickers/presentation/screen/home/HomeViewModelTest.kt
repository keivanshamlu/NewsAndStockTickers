package com.shamlou.keivan.newsAndTickers.presentation.screen.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.LoadState
import com.shamlou.keivan.domain.model.ErrorModel
import com.shamlou.keivan.domain.model.news.ArticlesDomain
import com.shamlou.keivan.domain.model.news.ResponseNewsDomain
import com.shamlou.keivan.domain.model.news.SourceDomain
import com.shamlou.keivan.domain.model.tickers.TickerDomain
import com.shamlou.keivan.domain.useCase.UseCaseGetNews
import com.shamlou.keivan.domain.useCase.UseCaseGetTickers
import com.shamlou.keivan.domain.util.Resource
import com.shamlou.keivan.newsAndTickers.presentation.screen.home.utility.CoroutineTestRule
import com.shamlou.keivan.newsAndTickers.presentation.screen.home.utility.getLastEmitted
import com.shamlou.keivan.newsAndTickers.presentation.screen.home.utility.getListEmitted
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var useCaseGetNews: UseCaseGetNews

    @MockK(relaxed = true)
    lateinit var useCaseGetTickers: UseCaseGetTickers

    lateinit var viewModel: HomeViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)

    }

    @Test
    fun shouldGetNews_WhenStarted() = mainCoroutineRule.testDispatcher.runBlockingTest {


        viewModel = HomeViewModel(
            useCaseGetTickers, useCaseGetNews
        )

        coVerify {
            useCaseGetNews.execute(Unit)
        }
    }
    @Test
    fun getTickers_shouldCallGetTickerUseCase() = mainCoroutineRule.testDispatcher.runBlockingTest {


        viewModel = HomeViewModel(
            useCaseGetTickers, useCaseGetNews
        )

        viewModel.getTickers()

        coVerify {
            useCaseGetTickers.execute(Unit)
        }
    }
    val validTickerList = listOf(
        TickerDomain("stock1", "price1"),
        TickerDomain("stock2", "price2"),
        TickerDomain("stock3", "price3"),
    )
    @Test
    fun getTickers_shouldExposeNewTickersToView() = mainCoroutineRule.testDispatcher.runBlockingTest {

        coEvery {
            useCaseGetTickers.execute(Unit)
        } returns flow { emit(Resource.success(validTickerList)) }

        viewModel = HomeViewModel(
            useCaseGetTickers, useCaseGetNews
        )

        viewModel.getTickers()

        val lastEmitted = getListEmitted(viewModel.tickers)
        Assert.assertEquals(lastEmitted.first().status, Resource.Status.SUCCESS)
        Assert.assertEquals(lastEmitted.first().data, validTickerList)
    }
    val sampleErrorMessage = "this is a sample error message"

    @Test
    fun getTickers_shouldExposeErrorToView_whenException() = mainCoroutineRule.testDispatcher.runBlockingTest {

        coEvery {
            useCaseGetTickers.execute(Unit)
        } returns flow { emit(Resource.error(ErrorModel(sampleErrorMessage))) }

        viewModel = HomeViewModel(
            useCaseGetTickers, useCaseGetNews
        )

        viewModel.getTickers()

        val lastEmitted = getListEmitted(viewModel.tickers)
        Assert.assertEquals(lastEmitted.first().status, Resource.Status.ERROR)
        Assert.assertEquals(lastEmitted.first().data, null)
        Assert.assertEquals(lastEmitted.first().error?.message, sampleErrorMessage)
    }

    val validResponse = ResponseNewsDomain(
        "status",8,
        arrayListOf(
            ArticlesDomain(SourceDomain("id", "source name"), ),
            ArticlesDomain(SourceDomain("id2", "source name2"), ),
            ArticlesDomain(SourceDomain("id3", "source name3"), ),
            ArticlesDomain(SourceDomain("id4", "source name4"), ),
            ArticlesDomain(SourceDomain("id5", "source name5"), ),
            ArticlesDomain(SourceDomain("id6", "source name6"), ),
            ArticlesDomain(SourceDomain("id7", "source name7"), ),
            ArticlesDomain(SourceDomain("id8", "source name8"), ),
        )
    )
    @Test
    fun getNews_shouldExposeNewsToView() = mainCoroutineRule.testDispatcher.runBlockingTest {

        coEvery {
            useCaseGetNews.execute(Unit)
        } returns flow { emit(Resource.success(validResponse)) }

        viewModel = HomeViewModel(
            useCaseGetTickers, useCaseGetNews
        )

        val lastEmitted = getListEmitted(viewModel.news)
        Assert.assertEquals(lastEmitted.first().status, Resource.Status.SUCCESS)
        Assert.assertEquals(lastEmitted.first().data, validResponse)
    }
    @Test
    fun getNews_shouldExposeFirst6NewsToView() = mainCoroutineRule.testDispatcher.runBlockingTest {

        coEvery {
            useCaseGetNews.execute(Unit)
        } returns flow { emit(Resource.success(validResponse)) }

        viewModel = HomeViewModel(
            useCaseGetTickers, useCaseGetNews
        )

        val lastEmitted = getListEmitted(viewModel.topNews)

        //only first 6
        Assert.assertEquals(lastEmitted.first(), mutableListOf(
            ArticlesDomain(SourceDomain("id", "source name"), ),
            ArticlesDomain(SourceDomain("id2", "source name2"), ),
            ArticlesDomain(SourceDomain("id3", "source name3"), ),
            ArticlesDomain(SourceDomain("id4", "source name4"), ),
            ArticlesDomain(SourceDomain("id5", "source name5"), ),
            ArticlesDomain(SourceDomain("id6", "source name6"), ))
        )

    }
    @Test
    fun getNews_shouldExposeFirstAfter6Items() = mainCoroutineRule.testDispatcher.runBlockingTest {

        coEvery {
            useCaseGetNews.execute(Unit)
        } returns flow { emit(Resource.success(validResponse)) }

        viewModel = HomeViewModel(
            useCaseGetTickers, useCaseGetNews
        )

        val lastEmitted = getListEmitted(viewModel.bottomNews)


        Assert.assertEquals(lastEmitted.first(), mutableListOf(
            ArticlesDomain(SourceDomain("id7", "source name7"), ),
            ArticlesDomain(SourceDomain("id8", "source name8"), ))
        )
    }
}