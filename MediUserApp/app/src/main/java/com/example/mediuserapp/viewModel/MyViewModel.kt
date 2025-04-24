package com.example.mediuserapp.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.ResultState
import com.example.mediuserapp.Network.response.AllProductsResponse
import com.example.mediuserapp.Network.response.CreateOrderResponse
import com.example.mediuserapp.Network.response.CreateUserResponse
import com.example.mediuserapp.Network.response.GetAllOrdersResponse
import com.example.mediuserapp.Network.response.GetSpecificAvailableProduct
import com.example.mediuserapp.Network.response.LoginUserResponse
import com.example.mediuserapp.Network.response.SpecificProductResponse
import com.example.mediuserapp.Network.response.SpecificUserResponse
import com.example.mediuserapp.Network.response.UserAvailableProductResponse
import com.example.mediuserapp.Repo.Repo
import com.example.pref.PreferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MyViewModel(
    private val preferenceDatastore : PreferencesDataStore
) : ViewModel() {
    val repo = Repo()
    private val _createUserState = MutableStateFlow<CreateUserState?>(null)
    val createUserState = _createUserState.asStateFlow()
    private val _loginUserState = MutableStateFlow<LoginUserState?>(null)
    val loginUserState = _loginUserState.asStateFlow()
    private val _specificUserState = MutableStateFlow<SpecificUserState?>(null)
    val specificUserState = _specificUserState.asStateFlow()
    private val _getAllProductsState = MutableStateFlow<GetAllProducts?>(null)
    val getAllProductsState = _getAllProductsState.asStateFlow()
    private val _getSpecificProductState =
        MutableStateFlow<GetSpecificAvailableProductViewModel?>(null)
    val getSpecificProductState = _getSpecificProductState.asStateFlow()
    private val _getUserAvailableProductState = MutableStateFlow<GetUserAvailableProduct?>(null)
    val getUserAvailableProductState = _getUserAvailableProductState.asStateFlow()
    private val _createOrderState = MutableStateFlow<CreateOrder?>(null)
    val createOrderState = _createOrderState.asStateFlow()
    private val _getAllOrdersState = MutableStateFlow<AllOrders?>(null)
    val getAllOrdersState = _getAllOrdersState.asStateFlow()


    var userId = mutableStateOf("")
    var userName = mutableStateOf("")


    suspend fun getUserIdDirectly(): String? {
        return preferenceDatastore.PrefUserid.first()
    }

    fun createUser(
        name: String,
        password: String,
        phoneNumber: String,
        email: String,
        pincode: String,
        address: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response =
                repo.createUser(name, password, phoneNumber, email, pincode, address).collect {
                    when (it) {
                        is ResultState.Loading -> {
                            _createUserState.value = CreateUserState(isLoading = true)
                        }

                        is ResultState.Success -> {
                            if(it.data.status == 200){
                                preferenceDatastore.saveUserId(it.data.message)
                            }
                            preferenceDatastore.saveUserId(it.data.message)
                            _createUserState.value =
                                CreateUserState(success = it.data, isLoading = false)
                        }

                        is ResultState.Error -> {
                            _createUserState.value =
                                CreateUserState(error = it.exception.message, isLoading = false)
                        }
                    }
                }



        }
    }


    fun loginUser(
        email: String,
        password: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.loginUser(email, password).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _loginUserState.value = LoginUserState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        if(it.data.status == 200){
                            preferenceDatastore.saveUserId(it.data.message)
                        }
                        _loginUserState.value = LoginUserState(success = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _loginUserState.value =
                            LoginUserState(error = it.exception.message, isLoading = false)
                    }
                }
//            Log.d("TAG Login", "loginUser: ${response.body()}")
            }

        }

    }

    fun getSpecificUser(user_id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.specificUser(user_id).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _specificUserState.value = SpecificUserState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _specificUserState.value =
                            SpecificUserState(success = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _specificUserState.value =
                            SpecificUserState(error = it.exception.message, isLoading = false)
                    }
                }
            }
        }
    }

    fun getSpecificProduct(product_id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getSpecificAvailableProduct(product_id).collect {

                when (it) {
                    is ResultState.Loading -> {
                        _getSpecificProductState.value =
                            GetSpecificAvailableProductViewModel(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getSpecificProductState.value =
                            GetSpecificAvailableProductViewModel(
                                success = it.data,
                                isLoading = false
                            )
                    }

                    is ResultState.Error -> {
                        _getSpecificProductState.value =
                            GetSpecificAvailableProductViewModel(
                                error = it.exception.message,
                                isLoading = false
                            )
                    }
                }
            }

        }
    }

    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getAllProducts().collect {
                when (it) {
                    is ResultState.Loading -> {
                        _getAllProductsState.value = GetAllProducts(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getAllProductsState.value =
                            GetAllProducts(success = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _getAllProductsState.value =
                            GetAllProducts(error = it.exception.message, isLoading = false)
                    }

                }

            }

        }
    }

    fun getUserAvailableProduct(user_id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getUserAvailableProduct().collect {
                when (it) {
                    is ResultState.Loading -> {
                        _getUserAvailableProductState.value =
                            GetUserAvailableProduct(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getUserAvailableProductState.value =
                            GetUserAvailableProduct(success = it.data, isLoading = false)

                    }

                    is ResultState.Error -> {
                        _getUserAvailableProductState.value =
                            GetUserAvailableProduct(error = it.exception.message, isLoading = false)
                    }
                }
            }
        }

    }

    fun resetOrderState(){
        _createOrderState.value = null
    }

    fun createOrder(
        user_id: String,
        product_id: String,
        quantity: String,
        price: String,
        product_name: String,
        user_name: String,
        message: String,
        category: String
    ) {
        val response = viewModelScope.launch(Dispatchers.IO) {
            repo.createOrder(
                user_id = user_id,
                product_id = product_id,
                quantity = quantity,
                price = price,
                product_name = product_name,
                user_name = user_name,
                message = message,
                category = category,
            ).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _createOrderState.value = CreateOrder(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _createOrderState.value = CreateOrder(success = it.data, isLoading = false)
                    }
                    is ResultState.Error -> {
                        _createOrderState.value =
                            CreateOrder(error = it.exception.message, isLoading = false)
                    }
                }

            }
        }
    }

    fun allOrder(){
        val response = viewModelScope.launch(Dispatchers.IO){
            repo.getAllOrders(userId.value).collect{
                when(it) {
                    is ResultState.Loading -> {
                        _getAllOrdersState.value = AllOrders(isLoading = true)
                    }
                    is ResultState.Success -> {
                        _getAllOrdersState.value = AllOrders(success = it.data, isLoading = false)
                    }
                    is ResultState.Error -> {
                        _getAllOrdersState.value =
                            AllOrders(error = it.exception.message, isLoading = false)
                    }
                }

            }
        }
    }


}

data class LoginUserState(
    val isLoading: Boolean = false,
    val success: LoginUserResponse? = null,
    val error: String? = null
)

data class CreateUserState(
    val isLoading: Boolean = false,
    val success: CreateUserResponse? = null,
    val error: String? = null
)

data class SpecificUserState(
    val isLoading: Boolean = false,
    val success: SpecificUserResponse? = null,
    val error: String? = null

)

data class GetAllProducts(
    val isLoading: Boolean = false,
    val success: AllProductsResponse? = null,
    val error: String? = null
)


data class GetSpecificAvailableProductViewModel(
    val isLoading: Boolean = false,
    val success: GetSpecificAvailableProduct? = null,
    val error: String? = null
)

data class GetUserAvailableProduct(
    val isLoading: Boolean = false,
    val success: UserAvailableProductResponse? = null,
    val error: String? = null
)

data class CreateOrder(
    val isLoading: Boolean = false,
    val success: CreateOrderResponse? = null,
    val error: String? = null
)

data class AllOrders(
    val isLoading: Boolean = false,
    val success: GetAllOrdersResponse? = null,
    val error: String? = null

)