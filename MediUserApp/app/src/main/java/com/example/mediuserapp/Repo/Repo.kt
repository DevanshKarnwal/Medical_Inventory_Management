package com.example.mediuserapp.Repo

import com.example.common.ResultState
import com.example.mediuserapp.Network.ApiProvider
import com.example.mediuserapp.Network.response.AllProductsResponse
import com.example.mediuserapp.Network.response.CreateOrderResponse
import com.example.mediuserapp.Network.response.CreateUserResponse
import com.example.mediuserapp.Network.response.GetAllOrdersResponse
import com.example.mediuserapp.Network.response.GetSpecificAvailableProduct
import com.example.mediuserapp.Network.response.LoginUserResponse
import com.example.mediuserapp.Network.response.SpecificUserResponse
import com.example.mediuserapp.Network.response.UpdateUserResponse
import com.example.mediuserapp.Network.response.UserAvailableProductResponse
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
    ): Flow<ResultState<CreateUserResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response = ApiProvider.provideApiService().createUser(
                name, password, phoneNumber, email, pincode, address
            )
            emit(ResultState.Success(response.body()!!))
        } catch (e: Exception) {
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
    ): Flow<ResultState<SpecificUserResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response = ApiProvider.provideApiService().specificUser(user_id)
            emit(ResultState.Success(response.body()!!))
        } catch (e: Exception) {
            emit(ResultState.Error(e))

        }
    }

    suspend fun getAllProducts(): Flow<ResultState<AllProductsResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response = ApiProvider.provideApiService().getAllProducts()
            if (response.isSuccessful && response.body() != null)
                emit(ResultState.Success(response.body()!!))
            else
                emit(ResultState.Error(Exception(response.message())))
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }

    suspend fun getSpecificAvailableProduct(product_id: String): Flow<ResultState<GetSpecificAvailableProduct>> =
        flow {
            emit(ResultState.Loading)
            try {
                val response = ApiProvider.provideApiService().getSpecificProduct(product_id)
                if (response.isSuccessful && response.body() != null)
                    emit(ResultState.Success(response.body()!!))
                else
                    emit(ResultState.Error(Exception(response.message())))
            } catch (e: Exception) {
                emit(ResultState.Error(e))
            }

        }

    suspend fun getUserAvailableProduct(): Flow<ResultState<UserAvailableProductResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response = ApiProvider.provideApiService().getUserAvailableProducts()
            if (response.isSuccessful && response.body() != null)
                emit(ResultState.Success(response.body()!!))
            else
                emit(ResultState.Error(Exception(response.message())))

        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }

    suspend fun createOrder(
        user_id: String,
        product_id: String,
        quantity: String,
        price: String,
        product_name: String,
        user_name: String,
        message: String,
        category: String,
    ): Flow<ResultState<CreateOrderResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response = ApiProvider.provideApiService().createOrderDetails(
                user_id = user_id,
                product_id = product_id,
                quantity = quantity,
                price = price,
                product_name = product_name,
                user_name = user_name,
                message = message,
                category = category,
            )
            if (response.isSuccessful && response.body() != null)
                emit(ResultState.Success(response.body()!!))
            else
                emit(ResultState.Error(Exception(response.message())))
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }

    suspend fun getAllOrders(user_id: String): Flow<ResultState<GetAllOrdersResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response = ApiProvider.provideApiService().getAllOrders()
            if (response.isSuccessful && response.body() != null)
                emit(ResultState.Success(response.body()!!))
            else
                emit(ResultState.Error(Exception(response.message())))

        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }

    suspend fun updateUserResponseRepo(
        user_id: String,
        name: String,
        address: String,
        email: String,
        phoneNumber: String,
        pincode: String,
        password: String,
    ): Flow<ResultState<UpdateUserResponse>> = flow {
        emit(ResultState.Loading)
        try {
            val response = ApiProvider.provideApiService()
                .updateUser(user_id, name, address, email, phoneNumber, pincode, password)
            if (response.isSuccessful && response.body() != null)
                emit(ResultState.Success(response.body()!!))
            else
                emit(ResultState.Error(Exception(response.message())))

        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }

}