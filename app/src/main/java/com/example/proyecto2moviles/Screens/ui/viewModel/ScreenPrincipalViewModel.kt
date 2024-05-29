package com.example.proyecto2moviles.Screens.ui.viewModel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.proyecto2moviles.Data.model.User
import com.example.proyecto2moviles.Screens.ui.view.ScreenPrincipal

import com.google.gson.Gson

class ScreenPrincipalViewModel : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userJson = intent.getStringExtra("user")
        val user = Gson().fromJson(userJson, User::class.java)

        setContent {
            ScreenPrincipal(activity = this, user = user)
        }
    }
}
