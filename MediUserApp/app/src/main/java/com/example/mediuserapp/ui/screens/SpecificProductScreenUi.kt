package com.example.mediuserapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.mediuserapp.viewModel.MyViewModel

@Composable
fun SpecificProductScreenUi(productId: String, viewModel: MyViewModel) {
    val state = viewModel.getSpecificProductState.collectAsState()
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
    }
}