package com.example.mediadminapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.mediadminapp.viewModel.myViewModel

@Composable
fun UserDetailScreen(userId: String, viewModel: myViewModel) {
    val state = viewModel.specificUserState.collectAsState()
    val user = state.value?.success?.message
    val blockUserState = viewModel.blockUserState.collectAsState()
    val approveUserState = viewModel.approveUserState.collectAsState()
    LaunchedEffect(1) {
        viewModel.getSpecificUser(userId)
    }
    LaunchedEffect(blockUserState.value) {

        if (blockUserState.value?.success != null) {
            viewModel.getSpecificUser(userId)
        }
    }
    LaunchedEffect(approveUserState.value) {
        if (approveUserState.value?.success != null) {
            viewModel.getSpecificUser(userId)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Name : ")
            }
            append(user?.name)
        })
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Number : ")
            }
            append(user?.phoneNumber)
        })
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Email : ")
            }
            append(user?.email)
        })
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Pincode : ")
            }
            append(user?.pincode)
        })

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    viewModel.approveUser(userId, if (user?.isApproved == 1) 0 else 1)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (user?.isApproved == 1) Color.Green else Color.Red,
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(2.dp)
            ) {
                Text(text = if (user?.isApproved == 1) "Approved" else "Not Approved")
            }
            Button(
                onClick = {
                    viewModel.blockUser(userId, if (user?.block == 1) 0 else 1)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (user?.block == 1) Color.Red else Color.Green,
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(2.dp)
            ) {
                Text(text = if (user?.block == 1) "Blocked" else "Unblocked")
            }
        }

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text("Delete User")
        }


    }
}