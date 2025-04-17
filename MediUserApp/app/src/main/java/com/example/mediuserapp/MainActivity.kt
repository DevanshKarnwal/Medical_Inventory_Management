package com.example.mediuserapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.mediuserapp.navigation.navigate
import com.example.mediuserapp.ui.screens.LoginUi
import com.example.mediuserapp.ui.theme.MediUserAppTheme
import com.example.mediuserapp.viewModel.MyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel : MyViewModel by viewModels()
            MediUserAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    SignUpUi(viewModel)
                    navigate(viewModel)
                }
            }
        }
    }
}

