package com.example.mediadminapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.mediadminapp.network.response.GetAllOrdersResponseMessage
import com.example.mediadminapp.viewModel.myViewModel

@Composable
fun orderScreenUi(viewModel: myViewModel) {
    var approveState = viewModel.approveOrderUpdateState.collectAsState()
    var orderState = viewModel.getAllOrdersState.collectAsState()
    var updateProductStock = viewModel.updateProductStock.collectAsState()
    val orderListStatus = orderState.value?.success?.status
    var orderList = orderState.value?.success?.message
    var specificProduct = viewModel.getSpecificProductState.collectAsState()
    var stock = specificProduct.value?.success?.message?.stock
    var orderStock = remember { mutableStateOf(0) }
    var orderProductId = remember { mutableStateOf("") }
    LaunchedEffect(1) {
        viewModel.allOrder()
        viewModel.clearSpecificProductState()
        viewModel.clearUpdateState()
    }
    LaunchedEffect(approveState.value?.success) {
        approveState.value?.success?.let {
            Log.d("TAGA", "first approve state success: ${specificProduct.value?.success?.message}")
            viewModel.getSpecificProduct(orderProductId.value)
            Log.d("TAGA", "inside approve state success: ${specificProduct.value?.success?.message}")
        }
    }
    LaunchedEffect(specificProduct.value?.success) {
        specificProduct.value?.success?.let {
            Log.d("TAGA", "inside sepecic product success: ${specificProduct.value?.success?.message}")
            stock = specificProduct.value?.success?.message?.stock
            stock = stock?.minus(orderStock.value)
            viewModel.updateProductStockAndApproval(orderProductId.value,
                specificProduct.value?.success?.message?.name.toString(),
                specificProduct.value?.success?.message?.price.toString(),
                specificProduct.value?.success?.message?.category.toString(),
                stock.toString(),
            )
            Log.d("TAGA", "after update product success: ")
        }
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("This is order screen list")
        when {

            orderState.value?.isLoading == true || approveState.value?.isLoading == true || updateProductStock.value?.isLoading == true -> {
                CircularProgressIndicator()
            }

            orderState.value?.error?.isNotBlank() == true  -> {
                Text(text = orderState.value?.error.toString())
                Text("Order State Error")
            }
            approveState.value?.error?.isNotBlank() == true -> {
                Text(text = approveState.value?.error.toString())
                Text("Approve State Error")
            }
            updateProductStock.value?.error?.isNotBlank() == true -> {
                Text(text = updateProductStock.value?.error.toString())
                Text("Update State Error")
            }
            specificProduct.value?.error?.isNotBlank() == true -> {
                Text(text = specificProduct.value?.error.toString())
                Text("Specific Product State Error")
            }
            orderState.value?.success != null -> {
                if (orderListStatus == 404) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("No Product Found")
                    }
                } else if (orderListStatus == 200) {
                    orderList = orderList?.filter {
                        it.isApproved == 0
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(orderList!!) {
                            OrderCard(it, viewModel,orderStock,orderProductId)
                        }
                    }
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

        }
    }
}


@Composable
fun OrderCard(
    order: GetAllOrdersResponseMessage,
    viewModel: myViewModel,
    orderStock: MutableState<Int>,
    orderProductId: MutableState<String>
) {


    var scope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            Color.Red
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Product name : ")
                }
                append(order.product_name)
            })
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Quantity : ")
                }
                append(order.quantity.toString())
            })
            Button(onClick = {

                viewModel.approveOrderUpdate(order.order_id, 1)
                Log.d("TAGA", "OrderCard: ${order.order_id}")
                orderStock.value = order.quantity
                orderProductId.value = order.product_id
            }) {
                Text("Approve")
            }


        }
    }
}