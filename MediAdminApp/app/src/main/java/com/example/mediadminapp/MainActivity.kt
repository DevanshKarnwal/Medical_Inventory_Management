package com.example.mediadminapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mediadminapp.navigation.navigate
import com.example.mediadminapp.ui.theme.MediAdminAppTheme
import com.example.mediadminapp.viewModel.myViewModel
import kotlin.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel : myViewModel by viewModels()
            MediAdminAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    navigate(viewModel)
                }
            }
        }
    }
}

