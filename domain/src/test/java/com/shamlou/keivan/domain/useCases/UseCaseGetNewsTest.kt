package com.shamlou.keivan.domain.useCases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shamlou.keivan.domain.model.ErrorModel
import com.shamlou.keivan.domain.model.news.ArticlesDomain
import com.shamlou.keivan.domain.model.news.ResponseNewsDomain
import com.shamlou.keivan.domain.model.news.SourceDomain
import com.shamlou.keivan.domain.repository.HomeRepository
import com.shamlou.keivan.domain.useCase.UseCaseGetNews
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

class UseCaseGetNewsTest {

    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var useCaseGetNews: UseCaseGetNews

    @MockK(relaxed = true)
    lateinit var repository: HomeRepository


    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCaseGetNews = UseCaseGetNews(
            repository
        )
    }

    val validResponse = ResponseNewsDomain(
        "status",2,
        arrayListOf(
            ArticlesDomain(SourceDomain("id", "source name"), "author", "title", "desc", "url"),
            ArticlesDomain(SourceDomain("id2", "source name2"), "author2", "title2", "desc2", "url2")
        )
    )
    @Test
    fun invoke_shouldCallRepo() = mainCoroutineRule.testDispatcher.runBlockingTest {

        coEvery {
            repository.getNews()
        } returns flow { emit(Resource.success(validResponse)) }

        getListEmitted(useCaseGetNews.execute(Unit))

        coEvery { repository.getTickers() }
    }

    @Test
    fun invoke_shouldReturnValidStatusAndData_whenSuccess()= mainCoroutineRule.testDispatcher.runBlockingTest {

        coEvery {
            repository.getNews()
        } returns flow { emit(Resource.success(validResponse)) }

        val emmited = getListEmitted(useCaseGetNews.execute(Unit))

        Assert.assertEquals(emmited.first().data, validResponse)
        Assert.assertEquals(emmited.first().status, Resource.Status.SUCCESS)
    }
    val sampleErrorMessage = "this is a sample error message"
    @Test
    fun invoke_shouldReturnValidError_whenException()= mainCoroutineRule.testDispatcher.runBlockingTest {

        coEvery {
            repository.getNews()
        } returns flow { emit(Resource.error(ErrorModel(sampleErrorMessage))) }

        val emmited = getListEmitted(useCaseGetNews.execute(Unit))

        Assert.assertEquals(emmited.first().data, null)
        Assert.assertEquals(emmited.first().status, Resource.Status.ERROR)
        Assert.assertEquals(emmited.first().error?.message, sampleErrorMessage)
    }
}