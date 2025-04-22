package com.example.mediuserapp.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.ResultState
import com.example.mediuserapp.Network.response.AllProductsResponse
import com.example.mediuserapp.Network.response.CreateUserResponse
import com.example.mediuserapp.Network.response.LoginUserResponse
import com.example.mediuserapp.Network.response.SpecificProductResponse
import com.example.mediuserapp.Network.response.SpecificUserResponse
import com.example.mediuserapp.Repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    val repo = Repo()
    private val _createUserState = MutableStateFlow<CreateUserState?>(null)
    val createUserState = _createUserState.asStateFlow()
    private val _loginUserState = MutableStateFlow<LoginUserState?>(null)
    val loginUserState = _loginUserState.asStateFlow()
    private val _specificUserState = MutableStateFlow<SpecificUserState?>(null)
    val specificUserState = _specificUserState.asStateFlow()
    private val _getAllProductsState = MutableStateFlow<GetAllProducts?>(null)
    val getAllProductsState = _getAllProductsState.asStateFlow()
    private val _getSpecificProductState = MutableStateFlow<GetSpecificProduct?>(null)
    val getSpecificProductState = _getSpecificProductState.asStateFlow()


    fun createUser(
        name: String,
        password: String,
        phoneNumber: String,
        email: String,
        pincode: String,
        address: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.createUser(name, password, phoneNumber, email, pincode, address).collect {
                when(it){
                    is ResultState.Loading ->{
                        _createUserState.value = CreateUserState(isLoading = true)
                    }
                    is ResultState.Success ->{
                        _createUserState.value = CreateUserState(success = it.data,isLoading = false)
                    }
                    is ResultState.Error ->{
                        _createUserState.value = CreateUserState(error = it.exception.message, isLoading = false)
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
                        _loginUserState.value = LoginUserState(success = it.data,isLoading = false)
                    }

                    is ResultState.Error -> {
                        _loginUserState.value = LoginUserState(error = it.exception.message, isLoading = false)
                    }
                }
//            Log.d("TAG Login", "loginUser: ${response.body()}")
            }

        }

    }

    fun getSpecificUser(user_id : String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.specificUser(user_id).collect{
                    when(it){
                        is ResultState.Loading ->{
                            _specificUserState.value = SpecificUserState(isLoading = true)
                        }
                        is ResultState.Success ->{
                            _specificUserState.value = SpecificUserState(success = it.data, isLoading = false)
                        }
                        is ResultState.Error ->{
                            _specificUserState.value = SpecificUserState(error = it.exception.message, isLoading = false)
                        }
                    }
            }
        }
    }
    fun getSpecificProduct(product_id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getSpecificProduct(product_id).collect {

                when (it) {
                    is ResultState.Loading -> {
                        _getSpecificProductState.value = GetSpecificProduct(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _getSpecificProductState.value =
                            GetSpecificProduct(success = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _getSpecificProductState.value =
                            GetSpecificProduct(error = it.exception.message, isLoading = false)
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

}
data class LoginUserState(
    val isLoading: Boolean = false,
    val success : LoginUserResponse? = null,
    val error : String? = null
)

data class CreateUserState(
    val isLoading: Boolean = false,
    val success : CreateUserResponse? = null,
    val error : String? = null
)
data class SpecificUserState(
    val isLoading: Boolean = false,
    val success : SpecificUserResponse? = null,
    val error : String? = null

)

data class GetAllProducts(
    val isLoading: Boolean = false,
    val success: AllProductsResponse? = null,
    val error: String? = null
)


data class GetSpecificProduct(
    val isLoading: Boolean = false,
    val success: SpecificProductResponse? = null,
    val error: String? = null
)

