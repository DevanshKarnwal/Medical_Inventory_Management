package com.example.mediadminapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
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
import com.example.mediadminapp.viewModel.myViewModel

@Composable
fun TestUpdate(viewModel: myViewModel) {
    var state = viewModel.updateProductStock.collectAsState()
    var specific = viewModel.getSpecificProductState.collectAsState()
    var stock by remember { mutableStateOf(0) }
    LaunchedEffect(1) {
        viewModel.resetUpdateStockState()
        viewModel.getSpecificProduct("34227e22-c0d0-4fa5-bd15-b95a230c9622")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when{
            state.value?.isLoading == true -> {
                Text(text = "Loading")
            }
            state.value?.error?.isNotBlank() == true -> {
                Text(text = state?.value?.error.toString())
            }
            state.value?.success != null -> {
                Text(text = "Success ${state.value?.success?.message}")
            }
        }
        OutlinedTextField(
            value = stock.toString(),
            onValueChange = {
                stock = it.toInt()
            }
        )
        Button(onClick = {viewModel.updateProductStockAndApproval("34227e22-c0d0-4fa5-bd15-b95a230c9622",
            specific.value?.success?.message?.name.toString(),
            specific.value?.success?.message?.price.toString(),
            specific.value?.success?.message?.category.toString(),
            stock.toString(),

        )}) {
            Text("Update Stock")
        }
    }
}