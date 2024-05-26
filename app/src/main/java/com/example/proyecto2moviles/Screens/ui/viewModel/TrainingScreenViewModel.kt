package com.example.proyecto2moviles.Screens.ui.viewModel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.proyecto2moviles.Screens.ui.view.TrainingScreen

class TrainingScreenViewModel: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrainingScreen(activity = this)
        }
    }
}
