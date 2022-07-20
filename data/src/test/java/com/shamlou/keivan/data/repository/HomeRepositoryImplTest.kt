package com.shamlou.keivan.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.shamlou.keivan.data.utility.CoroutineTestRule
import com.shamlou.keivan.data.utility.getListEmitted
import com.shamlou.keivan.domain.repository.HomeRepository
import com.shamlou.keivan.domain.util.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class HomeRepositoryImplTest {

    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var repo: HomeRepository

    @MockK(relaxed = true)
    lateinit var fileReader: ReadFileFromAssets

    @MockK(relaxed = true)
    lateinit var gson: Gson

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = HomeRepositoryImpl(
            fileReader,
            gson,
            mainCoroutineRule.testDispatcherProvider
        )
    }

    val validJson = """"FORD", 72.19042874981784
"FORD", -211.5509976763925
"NVDA", 30.999642050562386
"NVDA", -150.5328741940824"""


    @Test
    fun getTickers_shouldCallFileReader_atFirst() =
        mainCoroutineRule.testDispatcher.runBlockingTest {

            coEvery {
                fileReader.readFile(
                    "stocks.txt"
                )
            } returns validJson

            val emittedList = getListEmitted(repo.getTickers())

            coVerify {
                fileReader.readFile(
                    "stocks.txt"
                )
            }
        }

    @Test
    fun getTickers_shouldotCallFileReader_afterCachingItInMemory() =
        mainCoroutineRule.testDispatcher.runBlockingTest {

            coEvery {
                fileReader.readFile(
                    "stocks.txt"
                )
            } returns validJson
            //calling twice
            getListEmitted(repo.getTickers())
            getListEmitted(repo.getTickers())

            //verify once
            coVerify(exactly = 1) {
                fileReader.readFile(
                    "stocks.txt"
                )
            }
        }
    @Test
    fun getTickers_shouldReturnSuccessState() =
        mainCoroutineRule.testDispatcher.runBlockingTest {

            coEvery {
                fileReader.readFile(
                    "stocks.txt"
                )
            } returns validJson

            val emitedItems = getListEmitted(repo.getTickers())

            //verify once
            Assert.assertEquals(emitedItems.first().status, Resource.Status.SUCCESS)
        }
    @Test
    fun getTickers_shouldReturnValidResponse() =
        mainCoroutineRule.testDispatcher.runBlockingTest {

            coEvery {
                fileReader.readFile(
                    "stocks.txt"
                )
            } returns validJson

            val emitedItems = getListEmitted(repo.getTickers())

            //verify once
            //in fake response we oly have 2 stocks
            Assert.assertEquals(emitedItems.first().data?.size, 2)
            Assert.assertTrue(emitedItems.first().data?.map { it.stock }?.contains("FORD") == true)
            Assert.assertTrue(emitedItems.first().data?.map { it.stock }?.contains("NVDA") == true)
            //we are calling random, but we are sure one of this conditions will happen
            Assert.assertTrue(emitedItems.first().data?.find { it.stock == "FORD" }?.price == "72.19" || emitedItems.first().data?.find { it.stock == "FORD" }?.price == "-211.55")
        }

    val sampleErrorMessage = "this is a sample error message"
    val sampleException = Exception(sampleErrorMessage)
    @Test
    fun getTickers_shouldReturnErrorStateWhenExceptionHappen() =
        mainCoroutineRule.testDispatcher.runBlockingTest {

            coEvery {
                fileReader.readFile(
                    "stocks.txt"
                )
            } throws sampleException

            val emitedItems = getListEmitted(repo.getTickers())

            //verify once
            Assert.assertEquals(emitedItems.first().status, Resource.Status.ERROR)
            Assert.assertEquals(emitedItems.first().error?.message, sampleErrorMessage)

        }
}