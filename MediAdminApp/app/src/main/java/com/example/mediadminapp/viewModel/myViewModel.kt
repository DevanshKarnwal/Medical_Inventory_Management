package com.example.mediadminapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediadminapp.common.ResultState
import com.example.mediadminapp.network.response.AllUsersResponse
import com.example.mediadminapp.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class myViewModel : ViewModel() {

    val repo = Repo()
    private val _allUsersState = MutableStateFlow(AllUsersState())
    val allUsersState = _allUsersState.asStateFlow()

    fun getAllUsers(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getAllUsers().collect {
                    when(it){
                        is ResultState.Loading -> {
                            _allUsersState.value = AllUsersState(isLoading = true)
                        }
                        is ResultState.Error -> {
                            _allUsersState.value = AllUsersState(error = it.exception.message, isLoading = false)
                        }
                        is ResultState.Success -> {
                            _allUsersState.value = AllUsersState(success = it.data, isLoading = false)
                        }
                    }
            }
        }
    }

}


data class AllUsersState(
    var isLoading: Boolean = false,
    var success : AllUsersResponse? = null,
    var error: String? = null
)