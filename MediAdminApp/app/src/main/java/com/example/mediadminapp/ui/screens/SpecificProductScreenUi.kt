package com.example.mediadminapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.mediadminapp.navigation.Routes
import com.example.mediadminapp.viewModel.myViewModel

@Composable
fun SpecificProductScreenUi(
    productId: String,
    viewModel: myViewModel,
    navController: NavHostController
) {
    val state = viewModel.getSpecificProductState.collectAsState()
    val deleteState = viewModel.deleteProductState.collectAsState()
    val product = state.value?.success?.message
    LaunchedEffect(1) {
        viewModel.getSpecificProduct(productId)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            state.value?.isLoading == true -> {
                CircularProgressIndicator()
            }

            state.value?.error != null -> {
                Text(text = state.value?.error.toString())
            }
            deleteState.value?.error != null -> {
                Text(text = deleteState.value?.error.toString())
            }
            deleteState.value?.success != null -> {
                Text(text = deleteState.value?.success.toString())
                navController.navigate(Routes.allProductScreenRoute)
            }
            deleteState.value?.isLoading == true -> {
                CircularProgressIndicator()
            }
        }
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Name : ")
            }
            append(product?.name)
        })
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Price : ")
            }
            append(product?.price.toString())
        })
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Category : ")
            }
            append(product?.category)
        })
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Stock : ")
            }
            append(product?.stock.toString())
        })
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {
                viewModel.deleteProduct(productId)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text("Delete Product")
        }
    }
}