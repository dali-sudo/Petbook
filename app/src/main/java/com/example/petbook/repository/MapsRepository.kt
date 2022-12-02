package com.example.petbook.repository
import android.util.Log
import com.example.petbook.dataapi.ApiInterface
import com.example.petbook.dataapi.GoogleApi
import com.example.petbook.model.BaseResponse
import com.example.petbook.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MapsRepository {

    fun getPlaces(url: String): Flow<State<Any>> = flow<State<Any>> {
        val GoogleApi = GoogleApi.create()
        emit(State.Loading(true))
        val response = GoogleApi.getNearByPlaces(url = url)

        Log.d("TAG", "getPlaces:  $response ")
        if (response.body()?.candidates?.size!! > 0) {
            Log.d(
                "TAG",
                "getPlaces:  Success called ${response.body()?.candidates?.size}"
            )

            emit(State.success(response.body()!!))
        } else {
            Log.d("TAG", "getPlaces:  failed called")
            emit(State.failed("No response !! an error has occured!!"))
        }


    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)

}