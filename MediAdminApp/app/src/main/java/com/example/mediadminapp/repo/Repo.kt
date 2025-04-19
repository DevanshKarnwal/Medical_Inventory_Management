package com.example.mediadminapp.repo

import android.util.Log
import com.example.mediadminapp.common.ResultState
import com.example.mediadminapp.network.ApiProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.mediadminapp.network.response.AllUsersResponse


class Repo {


    suspend fun getAllUsers() : Flow<ResultState<AllUsersResponse>> = flow {
        emit(ResultState.Loading)
        try {
            Log.d("response","before service call from repo")
            val response = ApiProvider.provideApiService().getAllUsers()
            Log.d("response",response.toString())
            emit(ResultState.Success(response.body()!!))

        }catch (e : Exception){
            Log.e("response", "Error occurred: ${e.message}  in repo error")
            emit(ResultState.Error(e))

        }
    }

}