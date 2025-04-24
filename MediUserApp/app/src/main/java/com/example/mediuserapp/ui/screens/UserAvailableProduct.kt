package com.example.mediuserapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mediuserapp.Network.response.MessageProduct
import com.example.mediuserapp.Network.response.UserAvailableProductMessage
import com.example.mediuserapp.navigation.Routes
import com.example.mediuserapp.viewModel.MyViewModel

@Composable
fun UserAvailableProductUi(viewModel: MyViewModel, navController: NavHostController) {
    val productState = viewModel.getUserAvailableProductState.collectAsState()
    val productListStatus = productState.value?.success?.status
    var productList = productState.value?.success?.message

    LaunchedEffect(1) {
        viewModel.getUserAvailableProduct(viewModel.userId.value)
    }
    Column(modifier = Modifier.fillMaxSize(), ) {
        when{
            productState.value?.isLoading == true -> {
                CircularProgressIndicator()
            }
            productState.value?.error?.isNotBlank() == true -> {
                Text(text = productState.value?.error.toString())
            }
            productState.value?.success != null -> {
                if (productListStatus == 404) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("No Product Found")
                    }
                } else if (productListStatus == 200) {
                    productList = productList?.filter{
                        it.user_id == viewModel.userId.value
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(productList!!) {
                            UserProductCard(product = it, navController)
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
fun UserProductCard(product : UserAvailableProductMessage,navController: NavHostController){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Routes.specificProductScreenRoute(product.product_id)) },
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                    append("Name : ")
                }
                append(product.name)
            })
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Stock : ")
                }
                append(product.stock.toString())
            })

        }
    }
}