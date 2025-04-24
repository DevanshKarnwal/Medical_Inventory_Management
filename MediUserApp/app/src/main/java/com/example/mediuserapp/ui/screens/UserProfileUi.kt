package com.example.mediuserapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.mediuserapp.Network.response.Message
import com.example.mediuserapp.viewModel.MyViewModel

@Composable
fun userProfileUi(viewModel: MyViewModel, navController: NavHostController) {

    var specificUserState = viewModel.specificUserState.collectAsState()
    var userData = specificUserState.value?.success?.message
    var status = specificUserState.value?.success?.status


    LaunchedEffect(1) {
        viewModel.getSpecificUser(viewModel.userId.value.toString())
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            specificUserState.value?.isLoading == true -> {
                CircularProgressIndicator()
            }

            specificUserState.value?.error?.isNotBlank() == true -> {
                Text(text = specificUserState.value?.error.toString())
            }

            specificUserState.value?.success != null -> {
                if (status == 400) {
                    Text("User not found")
                } else if (status == 200) {
                    updateProfileDataUi(viewModel,navController,userData)
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
fun updateProfileDataUi(
    viewModel: MyViewModel,
    navController: NavHostController,
    userData: Message?,
) {
    var name by remember { mutableStateOf(userData?.name) }
    var address by remember { mutableStateOf(userData?.address) }
    var email by remember { mutableStateOf(userData?.email) }
    var phoneNumber by remember { mutableStateOf(userData?.phoneNumber) }
    var pinCode by remember { mutableStateOf(userData?.pincode) }
    var password by remember { mutableStateOf(userData?.password) }

    OutlinedTextField(
        value = name.toString(), onValueChange = {name = it},label = { Text("Name") }
    )
    OutlinedTextField(
        value = address.toString(), onValueChange = {address = it},label = { Text("Address") }
    )
    OutlinedTextField(
        value = email.toString(), onValueChange = {email = it},label = { Text("Email") }
    )
    OutlinedTextField(
        value = phoneNumber.toString(), onValueChange = {phoneNumber = it},label = { Text("Phone Number") }
    )
    OutlinedTextField(
        value = pinCode.toString(), onValueChange = {pinCode = it},label = { Text("Pin Code") }
    )
    OutlinedTextField(
        value = password.toString(), onValueChange = {password = it},label = { Text("Password") }
    )

    Button(
        onClick = {
            viewModel.updateUserFunction(
                user_id = viewModel.userId.value.toString(),
                name = name.toString(),
                address = address.toString(),
                email = email.toString(),
                phoneNumber = phoneNumber.toString(),
                pincode = pinCode.toString(),
                password = password.toString(),
            )
        }
    ) {
        "Update Profile"
    }


}