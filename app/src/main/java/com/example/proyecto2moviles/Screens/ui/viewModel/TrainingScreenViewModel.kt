package com.example.proyecto2moviles.Screens.ui.viewModel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.proyecto2moviles.Screens.ui.view.TrainingScreen
import com.google.firebase.database.FirebaseDatabase

class TrainingScreenViewModel: ComponentActivity() {
    private val databaseReference = FirebaseDatabase.getInstance().getReference("Training")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            TrainingScreen(databaseReference)

        }
    }
}
