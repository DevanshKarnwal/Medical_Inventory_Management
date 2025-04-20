package com.example.mediadminapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavController
import com.example.mediadminapp.navigation.Routes
import com.example.mediadminapp.viewModel.myViewModel

@Composable
fun addProductScreenUi(viewModel: myViewModel, navController: NavController) {

    val state = viewModel.createProductState.collectAsState()
    val name = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }
    val stock = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current


    when {
        state.value?.isLoading == true -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.value?.error != null -> {
            Text(text = state.value?.error.toString())
        }
        state.value?.success != null -> {
            Text(text = state.value?.success!!.message)
            if (state.value?.success!!.status == 200){
                    navController.navigate(Routes.allProductScreenRoute)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Name") },
            placeholder = { Text("Enter Name") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Down) }
        )
        OutlinedTextField(
            value = price.value,
            onValueChange = { price.value = it },
            label = { Text("Price") },
            placeholder = { Text("Enter Price") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Down) }
        )
        OutlinedTextField(
            value = category.value,
            onValueChange = { category.value = it },
            label = { Text("Category") },
            placeholder = { Text("Enter Category") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Down) }
        )
        OutlinedTextField(
            value = stock.value,
            onValueChange = { stock.value = it },
            label = { Text("Stock") },
            placeholder = { Text("Stock") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        )
        Button(onClick = {
            viewModel.createProduct(name.value, price.value, category.value, stock.value)
        }) {
            Text(text = "Create Product Info")

        }
    }


}