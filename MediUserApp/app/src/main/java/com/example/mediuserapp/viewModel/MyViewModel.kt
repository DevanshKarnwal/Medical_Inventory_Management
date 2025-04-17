package com.example.mediuserapp.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediuserapp.Network.response.CreateUserResponse
import com.example.mediuserapp.Repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

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
    ){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.createUser(name, password, phoneNumber, email, pincode, address)
        }
    }

    fun loginUser(
        email: String,
        password: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.loginUser(email, password)
            Log.d("TAG Login", "loginUser: ${response.body()}")
        }

    }

}