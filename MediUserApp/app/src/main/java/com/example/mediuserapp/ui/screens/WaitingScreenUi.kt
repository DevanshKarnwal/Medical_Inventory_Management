package com.example.mediuserapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.mediuserapp.navigation.Routes
import com.example.mediuserapp.viewModel.MyViewModel

@Composable
fun waitingScreen(userId: String, viewModel: MyViewModel, navController: NavHostController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val state = viewModel.specificUserState.collectAsState()
        LaunchedEffect(key1 = Unit) {
            viewModel.getSpecificUser(userId)
        }
        Text("This is waiting Screen")
        when {
            state.value?.isLoading == true -> {
                CircularProgressIndicator()
            }

            state.value?.error != null -> {
                Text(text = state.value?.error.toString())
            }

            state.value?.success != null -> {
                if (state.value?.success!!.status == 200) {
                    viewModel.userName.value = state.value?.success!!.message.name
                    if (state.value?.success!!.message.isApproved == 0) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Currently you are not approved")
                            Button(onClick = {
                                viewModel.getSpecificUser(userId)
                            }) {
                                Text("Refresh")
                            }
                        }
                    } else {
                        navController.navigate(Routes.HomeScreen(userId))
                    }
                }
            }
        }
    }

}