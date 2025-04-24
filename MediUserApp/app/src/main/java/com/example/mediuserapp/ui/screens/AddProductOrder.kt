package com.example.mediuserapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.mediuserapp.Network.response.MessageProduct
import com.example.mediuserapp.navigation.Routes
import com.example.mediuserapp.viewModel.MyViewModel

@Composable
fun AddProductScreenUi(viewModel: MyViewModel, navController: NavHostController) {
    var allProductState = viewModel.getAllProductsState.collectAsState()
    var createOrderState = viewModel.createOrderState.collectAsState()
    val productList = allProductState.value?.success?.message
    val productListStatus = allProductState.value?.success?.status
    LaunchedEffect(1) {
        viewModel.getAllProducts()
    }
    LaunchedEffect(createOrderState.value?.success) {
        if (createOrderState.value?.success != null) {
            viewModel.resetOrderState()
            navController.navigate(Routes.ordersRoute)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when {
            allProductState.value?.isLoading == true -> {
                CircularProgressIndicator()
            }

            allProductState.value?.error != null -> {
                Text(text = allProductState.value?.error.toString())
            }

            allProductState.value?.success != null -> {
                if (productListStatus == 404) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("No Product Found")
                    }
                }
                else if (productListStatus == 200) {
                    DropDownWithInput(viewModel, productList!!)
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Some error occured")
                    }
                }

            }

            createOrderState.value?.isLoading == true -> {
                CircularProgressIndicator()
            }
            createOrderState.value?.error != null -> {
                Text(text = createOrderState.value?.error.toString())
            }
            createOrderState.value?.success != null -> {
                navController.navigate(Routes.ordersRoute)
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownWithInput(viewModel: MyViewModel, productList: List<MessageProduct>) {
    var selectedItem by remember { mutableStateOf("") }
    var selectedItemIdx by remember { mutableStateOf(0) }
    var selectedItemCount by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var max by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedItem,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Product") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                productList.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text("${item.name} - ${item.stock}") },
                        onClick = {
                            selectedItem = item.name
                            selectedItemIdx = index
                            max = item.stock
                            selectedItemCount = 0
                            expanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = selectedItemCount.toString(),
            onValueChange = { selectedItemCount = minOf(max, it.toIntOrNull() ?: 0) },
            label = { Text("Quantity") }
        )

        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Message") }
        )

        Button(onClick = {
            viewModel.createOrder(
                user_id = viewModel.userId.value,
                product_id = productList[selectedItemIdx].product_id,
                quantity = selectedItemCount.toString(),
                price = productList[selectedItemIdx].price.toString(),
                product_name = productList[selectedItemIdx].name,
                user_name = viewModel.userName.value,
                message = message,
                category = productList[selectedItemIdx].category
            )
        }) {
            Text(text = "Place Order")
        }
    }
}