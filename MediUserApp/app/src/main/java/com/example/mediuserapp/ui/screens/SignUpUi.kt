package com.example.mediuserapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mediuserapp.viewModel.MyViewModel

@Composable
fun SignUpUi(viewModel: MyViewModel) {
    val name = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val pincode = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Name") },
            placeholder = { Text("Enter Name") })
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            placeholder = { Text("Enter Password") })
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            placeholder = { Text("Enter Email") })
        OutlinedTextField(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            label = { Text("Phone Number") },
            placeholder = { Text("Enter Phone Number") })
        OutlinedTextField(
            value = pincode.value,
            onValueChange = { pincode.value = it },
            label = { Text("Pincode") },
            placeholder = { Text("Enter Pincode") })
        OutlinedTextField(
            value = address.value,
            onValueChange = { address.value = it },
            label = { Text("Address") },
            placeholder = { Text("Enter Address") })
        Button(onClick = {
            viewModel.createUser(
                name.value,
                password.value,
                phoneNumber.value,
                email.value,
                pincode.value,
                address.value
            )
        }) {
            Text("Create")
        }
    }

}