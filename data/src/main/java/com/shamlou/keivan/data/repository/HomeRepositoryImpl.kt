package com.shamlou.keivan.data.repository

import com.google.gson.Gson
import com.shamlou.keivan.domain.model.ErrorModel
import com.shamlou.keivan.domain.model.news.ResponseNewsDomain
import com.shamlou.keivan.domain.model.tickers.TickerDomain
import com.shamlou.keivan.domain.repository.HomeRepository
import com.shamlou.keivan.domain.util.DefaultDispatcherProvider
import com.shamlou.keivan.domain.util.DispatcherProvider
import com.shamlou.keivan.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.DecimalFormat
import java.util.*


class HomeRepositoryImpl(
    private val fileReader: ReadFileFromAssets,
    private val gson: Gson,
    private val dispatchers: DispatcherProvider = DefaultDispatcherProvider()
) : HomeRepository {


    // we store the tickers here and then we use this variable
    // so we can prevent repetitive call to read the file
    // it contains stock name as key and all possible price as set of float
    private val tickersHashMap = HashMap<String, MutableSet<Float>>()

    // will be called per second ,gets list of TickerDomain
    // first time reads from file and
    // then fills the tickersHashMap and then uses it ever since
    // each time goes through hashmap and randomly selects a price
    // for each stock and then returns list of TickerDomain
    override fun getTickers(): Flow<Resource<List<TickerDomain>>> = flow {

        // check whether items are already fetched or not
        // if items are not present, we go read file
        if (tickersHashMap.isEmpty()) {

            //calling file reader and reading the response
            fileReader.readFile("stocks.txt").let { response ->

                //going through each line
                val scanner = Scanner(response)
                while (scanner.hasNextLine()) {
                    val line: String = scanner.nextLine()

                    // split the line
                    line.split(",").let {
                        //specifying the stock
                        val stock = it.first().subSequence(1 until it.first().length - 1).toString()
                        //adding it to hash map
                        val lastValue = tickersHashMap[stock] ?: mutableSetOf()
                        lastValue.add(it[1].toFloat())
                        tickersHashMap[stock] = lastValue

                    }
                }
                scanner.close()
            }
        }

        val result = mutableListOf<TickerDomain>()

        // going through every key (all stock names) and
        // select a random price and add it to result
        for (key in tickersHashMap.keys) {

            // if for any reason price was not present, we generate a random price
            val randomPrice = tickersHashMap[key]?.random() ?: (Random().nextFloat() * 50 + 50);
            result.add(TickerDomain(key, DecimalFormat("#.##").format(randomPrice)))
        }

        //emitting the result
        emit(Resource.success(result))
    }.flowOn(dispatchers.io())
        .catch {

        //emitting the error
        emit(Resource.error(ErrorModel(it.message ?: "")))
    }

    override fun getNews(): Flow<Resource<ResponseNewsDomain>> = flow {

        emit(Resource.loading())
        val response = fileReader.readFile("news.json")
        emit(Resource.success(convertJsonToNewsDomain(response)))
    }.flowOn(dispatchers.io())
        .catch {

        //emitting the error
        emit(Resource.error(ErrorModel(it.message ?: "")))
    }

    // gets a json string and returns list of kotlin objects
    private fun convertJsonToNewsDomain(jsonResponse: String): ResponseNewsDomain? {
        return gson.fromJson(jsonResponse, ResponseNewsDomain::class.java)
    }
}