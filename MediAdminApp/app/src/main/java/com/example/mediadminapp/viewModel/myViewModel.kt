package com.example.mediadminapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediadminapp.common.ResultState
import com.example.mediadminapp.network.response.AllProductsResponse
import com.example.mediadminapp.network.response.AllUsersResponse
import com.example.mediadminapp.network.response.ApproveUserResponse
import com.example.mediadminapp.network.response.BlockUserResponse
import com.example.mediadminapp.network.response.CreateProductResponse
import com.example.mediadminapp.network.response.SpecificUserResponse
import com.example.mediadminapp.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class myViewModel : ViewModel() {

    val repo = Repo()
    private val _allUsersState = MutableStateFlow(AllUsersState())
    val allUsersState = _allUsersState.asStateFlow()
    private val _specificUserState = MutableStateFlow<SpecificUserState?>(null)
    val specificUserState = _specificUserState.asStateFlow()
    private val _blockUserState = MutableStateFlow<BlockState?>(null)
    val blockUserState = _blockUserState.asStateFlow()
    private val _approveUserState = MutableStateFlow<ApproveState?>(null)
    val approveUserState = _approveUserState.asStateFlow()
    private val _createProductState = MutableStateFlow<CreateProduct?>(null)
    val createProductState = _createProductState.asStateFlow()
    private val _getAllProductsState = MutableStateFlow<GetAllProducts?>(null)
    val getAllProductsState = _getAllProductsState.asStateFlow()

    fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getAllUsers().collect {
                when (it) {
                    is ResultState.Loading -> {
                        _allUsersState.value = AllUsersState(isLoading = true)
                    }

                    is ResultState.Error -> {
                        _allUsersState.value =
                            AllUsersState(error = it.exception.message, isLoading = false)
                    }

                    is ResultState.Success -> {
                        _allUsersState.value = AllUsersState(success = it.data, isLoading = false)
                    }
                }
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

    fun blockUser(user_id: String, block: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.blockUser(user_id, block.toString()).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _blockUserState.value = BlockState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _blockUserState.value = BlockState(success = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _blockUserState.value =
                            BlockState(error = it.exception.message, isLoading = false)
                    }
                }
            }

        }
    }

    fun approveUser(user_id: String, approve: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.approveUser(user_id, approve.toString()).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _approveUserState.value = ApproveState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _approveUserState.value = ApproveState(success = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _approveUserState.value =
                            ApproveState(error = it.exception.message, isLoading = false)
                    }
                }
            }

        }
    }

    fun createProduct(name: String, price: String, category: String, stock: String) {
        viewModelScope.launch(Dispatchers.IO) {
             repo.createProduct(name, price, category, stock).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _createProductState.value = CreateProduct(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _createProductState.value =
                            CreateProduct(success = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _createProductState.value =
                            CreateProduct(error = it.exception.message, isLoading = false)
                    }

                }
            }
        }
    }

    fun getAllProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getAllProducts().collect{
                when(it){
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


data class AllUsersState(
    var isLoading: Boolean = false,
    var success: AllUsersResponse? = null,
    var error: String? = null
)

data class SpecificUserState(
    val isLoading: Boolean = false,
    val success: SpecificUserResponse? = null,
    val error: String? = null
)

data class BlockState(
    val isLoading: Boolean = false,
    val success: BlockUserResponse? = null,
    val error: String? = null
)

data class ApproveState(
    val isLoading: Boolean = false,
    val success: ApproveUserResponse? = null,
    val error: String? = null
)

data class CreateProduct(
    val isLoading: Boolean = false,
    val success: CreateProductResponse? = null,
    val error: String? = null
)

data class GetAllProducts(
    val isLoading: Boolean = false,
    val success: AllProductsResponse? = null,
    val error: String? = null
)
