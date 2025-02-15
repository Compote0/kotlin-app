package com.example.courskotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.courskotlin.ui.screens.MainScreen
import com.example.courskotlin.ui.screens.SearchScreen
import com.example.courskotlin.ui.theme.app_theme
import com.example.courskotlin.ui.theme.app_theme
import com.example.courskotlin.viewmodel.MainViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            app_theme {
                MainScreen(mainViewModel = MainViewModel(isPreview = false))
            }
        }
    }
}