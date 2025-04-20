package com.example.mediuserapp.Repo

import com.example.common.ResultState
import com.example.mediuserapp.Network.ApiProvider
import com.example.mediuserapp.Network.response.CreateUserResponse
import com.example.mediuserapp.Network.response.LoginUserResponse
import com.example.mediuserapp.Network.response.SpecificUserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repo {
    suspend fun createUser(
        name: String,
        password: String,
        phoneNumber: String,
        email: String,
        pincode: String,
        address: String
    ) : Flow<ResultState<CreateUserResponse>> = flow {
            emit(ResultState.Loading)
        try{
            val response = ApiProvider.provideApiService().createUser(
                name, password, phoneNumber, email, pincode, address
            )
            emit(ResultState.Success(response.body()!!))
        }catch (e : Exception){
            emit(ResultState.Error(e))
        }
    }

    suspend fun loginUser(
        email: String,
        password: String
    ): Flow<ResultState<LoginUserResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response = ApiProvider.provideApiService().loginUser(
                email, password
            )
            emit(ResultState.Success(response.body()!!))
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }

    suspend fun specificUser(
        user_id: String
    ) : Flow<ResultState<SpecificUserResponse>> = flow{
        emit(ResultState.Loading)
        try {
            val response = ApiProvider.provideApiService().specificUser(user_id)
            emit(ResultState.Success(response.body()!!))
        }
        catch (e : Exception){
            emit(ResultState.Error(e))

        }
    }




}