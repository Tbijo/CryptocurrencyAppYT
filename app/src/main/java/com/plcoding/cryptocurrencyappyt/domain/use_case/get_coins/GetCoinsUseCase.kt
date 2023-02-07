package com.plcoding.cryptocurrencyappyt.domain.use_case.get_coins

import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.domain.model.Coin
import com.plcoding.cryptocurrencyappyt.domain.model.toCoin
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository // inject the Interface then it is easily replaceable
) {
    // use cases should have one public function
    // and that is the function to execute that use case
    // in this case to get the Coins

    //1. we are overriding the invoke function we can call GetCoinsUseCase as it was a function
    //2. we return a Flow because we want to emit multiple values at a time
        // first we emit that we are Loading the data -> to display a progress bar
        // when it is Succesful we want to emit our data List of Coins
        // if it is a Error we want to emit the Error
        // These three states will be the Resource
    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            // First there needs to be loading to display progressBar
            emit(Resource.Loading<List<Coin>>())
            // dalej volame data a transform z Dto na Coin
            val coins = repository.getCoins().map { it.toCoin() }
            // ak nepride chyba mozeme poslat data
            emit(Resource.Success<List<Coin>>(coins))
        } catch (e: HttpException) {
            // response code does not start with 2
            emit(Resource.Error<List<Coin>>(e.localizedMessage ?: "Unexpected Error has occured"))
        } catch (e: IOException) {
            // API has no connection to Remote Data
            emit(Resource.Error<List<Coin>>("Could not reach server, check internet connection"))
        }
    }
}