package com.example.mediuserapp.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.ResultState
import com.example.mediuserapp.Network.response.CreateUserResponse
import com.example.mediuserapp.Network.response.LoginUserResponse
import com.example.mediuserapp.Repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    val repo = Repo()
    val createUserState = mutableStateOf<CreateUserResponse?>(null)
    fun createUser(
        name: String,
        password: String,
        phoneNumber: String,
        email: String,
        pincode: String,
        address: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.createUser(name, password, phoneNumber, email, pincode, address)
        }
    }

    private val _loginUserState = MutableStateFlow<LoginUserState?>(null)
    val loginUserState = _loginUserState.asStateFlow()
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

}
data class LoginUserState(
    val isLoading: Boolean = false,
    val success : LoginUserResponse? = null,
    val error : String? = null
)