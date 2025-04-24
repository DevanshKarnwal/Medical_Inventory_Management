package com.example.mediuserapp

import android.os.Bundle
import android.preference.PreferenceDataStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.mediuserapp.navigation.navigate
import com.example.mediuserapp.ui.screens.LoginUi
import com.example.mediuserapp.ui.theme.MediUserAppTheme
import com.example.mediuserapp.viewModel.MyViewModel
import com.example.pref.PreferencesDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val userPreferenceDataStore = PreferencesDataStore(context)
//            val viewModel : MyViewModel by viewModels()
            val viewModel = MyViewModel(userPreferenceDataStore)
            MediUserAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    SignUpUi(viewModel)
                    navigate(viewModel)
                }
            }
        }
    }
}

