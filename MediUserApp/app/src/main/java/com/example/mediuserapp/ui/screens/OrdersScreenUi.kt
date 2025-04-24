package com.example.mediuserapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mediuserapp.Network.response.GetAllOrdersResponseMessage
import com.example.mediuserapp.Network.response.UserAvailableProductMessage
import com.example.mediuserapp.navigation.Routes
import com.example.mediuserapp.viewModel.MyViewModel

@Composable
fun OrderScreenUi(viewModel: MyViewModel, navController: NavHostController) {
    var orderState = viewModel.getAllOrdersState.collectAsState()
    val orderListStatus = orderState.value?.success?.status
    var orderList = orderState.value?.success?.message
    LaunchedEffect(1) {
        viewModel.allOrder()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("This is order screen list")
        when {
            orderState.value?.isLoading == true -> {
                CircularProgressIndicator()
            }

            orderState.value?.error?.isNotBlank() == true -> {
                Text(text = orderState.value?.error.toString())
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
                        it.user_id == viewModel.userId.value
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(orderList!!) {
                            OrderCard(it,navController)
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
fun OrderCard(order: GetAllOrdersResponseMessage, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
                   if(order.isApproved == 0)
                       Color.Red
                   else
                       Color.Green
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

        }
    }
}