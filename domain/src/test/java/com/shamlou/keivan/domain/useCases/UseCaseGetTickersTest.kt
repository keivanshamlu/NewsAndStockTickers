package com.shamlou.keivan.domain.useCases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shamlou.keivan.domain.model.ErrorModel
import com.shamlou.keivan.domain.model.tickers.TickerDomain
import com.shamlou.keivan.domain.repository.HomeRepository
import com.shamlou.keivan.domain.useCase.UseCaseGetTickers
import com.shamlou.keivan.domain.util.Resource
import com.shamlou.keivan.domain.utility.CoroutineTestRule
import com.shamlou.keivan.domain.utility.getListEmitted
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UseCaseGetTickersTest {

    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var useCaseGetTickers: UseCaseGetTickers

    @MockK(relaxed = true)
    lateinit var repository: HomeRepository


    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCaseGetTickers = UseCaseGetTickers(
            repository
        )
    }

    val validTickerList = listOf(
        TickerDomain("stock1", "price1"),
        TickerDomain("stock2", "price2"),
        TickerDomain("stock3", "price3"),
    )
    @Test
    fun invoke_shouldCallRepo() = mainCoroutineRule.testDispatcher.runBlockingTest {

        coEvery {
            repository.getTickers()
        } returns flow { emit(Resource.success(validTickerList)) }

        getListEmitted(useCaseGetTickers.execute(Unit))

        coEvery { repository.getTickers() }
    }

    @Test
    fun invoke_shouldReturnValidStatusAndData_whenSuccess()= mainCoroutineRule.testDispatcher.runBlockingTest {

        coEvery {
            repository.getTickers()
        } returns flow { emit(Resource.success(validTickerList)) }

        val emmited = getListEmitted(useCaseGetTickers.execute(Unit))

        Assert.assertEquals(emmited.first().data, validTickerList)
        Assert.assertEquals(emmited.first().status, Resource.Status.SUCCESS)
    }
    val sampleErrorMessage = "this is a sample error message"
    @Test
    fun invoke_shouldReturnValidError_whenException()= mainCoroutineRule.testDispatcher.runBlockingTest {

        coEvery {
            repository.getTickers()
        } returns flow { emit(Resource.error(ErrorModel(sampleErrorMessage))) }

        val emmited = getListEmitted(useCaseGetTickers.execute(Unit))

        Assert.assertEquals(emmited.first().data, null)
        Assert.assertEquals(emmited.first().status, Resource.Status.ERROR)
        Assert.assertEquals(emmited.first().error?.message, sampleErrorMessage)
    }
}