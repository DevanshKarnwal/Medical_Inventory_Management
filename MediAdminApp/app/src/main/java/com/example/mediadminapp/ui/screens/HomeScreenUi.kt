package com.example.mediadminapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import com.example.mediadminapp.navigation.Routes
import com.example.mediadminapp.network.response.Message
import com.example.mediadminapp.viewModel.myViewModel


@Composable
fun AllUsersUi(viewModel: myViewModel, navController: NavHostController) {
    val state = viewModel.allUsersState.collectAsState()
    val usersList = state.value.success?.message
    val usersListStatus = state.value.success?.status
    LaunchedEffect(1) {
        viewModel.getAllUsers()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        when {
            state.value?.isLoading == true -> {
                CircularProgressIndicator()
            }

            state.value?.error != null -> {
                Text(text = state.value?.error.toString())
            }

            state.value?.success != null -> {
                if (usersListStatus == 404) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("No User Found")
                    }
                } else if (usersListStatus == 200) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(usersList!!) {
                            UserInfoCard(user = it,navController)
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
fun UserInfoCard(user: Message, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Routes.userDetailScreenRoute(user.user_id)) },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Name : ")
                }
                append(user.name)
            })
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Number : ")
                }
                append(user.phoneNumber)
            })

        }
    }

}
