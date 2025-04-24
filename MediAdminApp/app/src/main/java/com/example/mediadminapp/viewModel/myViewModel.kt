package com.example.mediadminapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediadminapp.common.ResultState
import com.example.mediadminapp.network.response.AllProductsResponse
import com.example.mediadminapp.network.response.AllUsersResponse
import com.example.mediadminapp.network.response.ApproveUserResponse
import com.example.mediadminapp.network.response.BlockUserResponse
import com.example.mediadminapp.network.response.CreateProductResponse
import com.example.mediadminapp.network.response.DeleteProductResponse
import com.example.mediadminapp.network.response.DeleteUserResponse
import com.example.mediadminapp.network.response.GetAllOrdersResponse
import com.example.mediadminapp.network.response.OrderApprovalResponse
import com.example.mediadminapp.network.response.SpecificProductResponse
import com.example.mediadminapp.network.response.SpecificUserResponse
import com.example.mediadminapp.network.response.UpdateProductResponse
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
    private val _getSpecificProductState = MutableStateFlow<GetSpecificProduct?>(null)
    val getSpecificProductState = _getSpecificProductState.asStateFlow()
    private val _deleteProductState = MutableStateFlow<DeleteProduct?>(null)
    val deleteProductState = _deleteProductState.asStateFlow()
    private val _deleteUserState = MutableStateFlow<DeleteUser?>(null)
    val deleteUserState = _deleteUserState.asStateFlow()
    private val _getAllOrdersState = MutableStateFlow<AllOrders?>(null)
    val getAllOrdersState = _getAllOrdersState.asStateFlow()
    private val _approveOrderUpdateState = MutableStateFlow<ApproveOrderUpdate?>(null)
    val approveOrderUpdateState = _approveOrderUpdateState.asStateFlow()
    private val _updateProductStock =
        MutableStateFlow<updateProductStockView?>(null)
    val updateProductStock = _updateProductStock.asStateFlow()

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

    fun deleteProduct(product_id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.deleteProduct(product_id).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _deleteProductState.value = DeleteProduct(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _deleteProductState.value =
                            DeleteProduct(success = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _deleteProductState.value =
                            DeleteProduct(error = it.exception.message, isLoading = false)
                    }
                }
            }
        }

    }

    fun deleteUser(user_id: String) {
        viewModelScope.launch {
            val response = repo.deleteUser(user_id).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _deleteUserState.value = DeleteUser(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _deleteUserState.value = DeleteUser(success = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _deleteUserState.value =
                            DeleteUser(error = it.exception.message, isLoading = false)
                    }
                }
            }

        }
    }

    fun allOrder() {
        val response = viewModelScope.launch(Dispatchers.IO) {
            repo.getAllOrders().collect {
                when (it) {
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

    fun approveOrderUpdate(order_id: String, approve: Int) {
        val response = viewModelScope.launch(Dispatchers.IO) {
            repo.orderApprove(order_id, approve.toString()).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _approveOrderUpdateState.value = ApproveOrderUpdate(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _approveOrderUpdateState.value =
                            ApproveOrderUpdate(success = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        _approveOrderUpdateState.value =
                            ApproveOrderUpdate(error = it.exception.message, isLoading = false)
                    }
                }
            }
        }
    }

    fun resetUpdateStockState(){
        _updateProductStock.value = null
    }
    fun updateProductStockAndApproval(product_id: String,name: String,price: String,category: String, stock: String,) {
        Log.d("TAGA", "updateProductStock: $product_id $stock ")
        val response = viewModelScope.launch(Dispatchers.IO) {
            repo.updateProductStockRepo(product_id, name, price, category, stock).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _updateProductStock.value =
                            updateProductStockView(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _updateProductStock.value =
                            updateProductStockView(success = it.data, isLoading = false)
                        Log.d("TAGA", "onside success: $product_id $stock ")

                    }

                    is ResultState.Error -> {
                        _updateProductStock.value =
                            updateProductStockView(
                                error = it.exception.message,
                                isLoading = false
                            )
                    }
                }
            }
        }
    }

    fun clearSpecificProductState(){
        _getSpecificProductState.value = null
    }
    fun clearUpdateState(){
        _updateProductStock.value = null
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

data class GetSpecificProduct(
    val isLoading: Boolean = false,
    val success: SpecificProductResponse? = null,
    val error: String? = null
)

data class DeleteProduct(
    val isLoading: Boolean = false,
    val success: DeleteProductResponse? = null,
    val error: String? = null
)

data class DeleteUser(
    val isLoading: Boolean = false,
    val success: DeleteUserResponse? = null,
    val error: String? = null
)

data class AllOrders(
    val isLoading: Boolean = false,
    val success: GetAllOrdersResponse? = null,
    val error: String? = null
)

data class ApproveOrderUpdate(
    val isLoading: Boolean = false,
    val success: OrderApprovalResponse? = null,
    val error: String? = null
)

data class updateProductStockView(
    val isLoading: Boolean = false,
    val success: UpdateProductResponse? = null,
    val error: String? = null
)