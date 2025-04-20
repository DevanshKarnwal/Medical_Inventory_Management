package com.example.mediadminapp.repo

import android.util.Log
import com.example.mediadminapp.common.ResultState
import com.example.mediadminapp.network.ApiProvider
import com.example.mediadminapp.network.response.AllProductsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.mediadminapp.network.response.AllUsersResponse
import com.example.mediadminapp.network.response.ApproveUserResponse
import com.example.mediadminapp.network.response.BlockUserResponse
import com.example.mediadminapp.network.response.CreateProductResponse
import com.example.mediadminapp.network.response.SpecificUserResponse


class Repo {


    suspend fun getAllUsers(): Flow<ResultState<AllUsersResponse>> = flow {
        emit(ResultState.Loading)
        try {
            Log.d("response", "before service call from repo")
            val response = ApiProvider.provideApiService().getAllUsers()
            Log.d("response", response.toString())
            if (response.isSuccessful && response.body() != null)
                emit(ResultState.Success(response.body()!!))
            else
                emit(ResultState.Error(Exception(response.message())))

        } catch (e: Exception) {
            Log.e("response", "Error occurred: ${e.message}  in repo error")
            emit(ResultState.Error(e))

        }
    }

    suspend fun specificUser(
        user_id: String
    ): Flow<ResultState<SpecificUserResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response = ApiProvider.provideApiService().specificUser(user_id)
            emit(ResultState.Success(response.body()!!))
        } catch (e: Exception) {
            emit(ResultState.Error(e))

        }
    }

    suspend fun blockUser(
        user_id: String,
        block: String
    ): Flow<ResultState<BlockUserResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response = ApiProvider.provideApiService().blockUser(user_id, block)
            if (response.isSuccessful && response.body() != null)
                emit(ResultState.Success(response.body()!!))
            else
                emit(ResultState.Error(Exception(response.message())))
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }

    suspend fun approveUser(
        user_id: String,
        approve: String
    ): Flow<ResultState<ApproveUserResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response = ApiProvider.provideApiService().approveUser(user_id, approve)
            if (response.isSuccessful && response.body() != null)
                emit(ResultState.Success(response.body()!!))
            else
                emit(ResultState.Error(Exception(response.message())))
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }

    suspend fun createProduct(
        name: String,
        price: String,
        category: String,
        stock: String
    ): Flow<ResultState<CreateProductResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response =
                ApiProvider.provideApiService().createProduct(name, price, category, stock)
            if (response.isSuccessful && response.body() != null)
                emit(ResultState.Success(response.body()!!))
            else
                emit(ResultState.Error(Exception(response.message())))

        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }

    suspend fun getAllProducts() : Flow<ResultState<AllProductsResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response = ApiProvider.provideApiService().getAllProducts()
            if (response.isSuccessful && response.body() != null)
                emit(ResultState.Success(response.body()!!))
            else
                emit(ResultState.Error(Exception(response.message())))
        }
        catch (e : Exception){
            emit(ResultState.Error(e))
        }
    }

}