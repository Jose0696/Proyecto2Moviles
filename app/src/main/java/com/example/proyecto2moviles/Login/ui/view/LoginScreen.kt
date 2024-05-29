package com.example.proyecto2moviles.Login.ui.view

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto2moviles.Login.ui.viewModel.LoginViewModel
import com.example.proyecto2moviles.R
import com.example.proyecto2moviles.Screens.ui.viewModel.ScreenPrincipalViewModel
import com.google.firebase.database.DatabaseReference
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(context: Context, databaseReference: DatabaseReference) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var loginError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.a),
            contentDescription = "Login image",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Welcome",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Login to your account",
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = null
                loginError = null
            },
            label = { Text(text = "Email address", color = Color.White) },
            isError = emailError != null,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                errorBorderColor = Color.Red,
                cursorColor = Color.White
            )
        )
        if (emailError != null) {
            Text(text = emailError!!, color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = null
                loginError = null
            },
            label = { Text(text = "Password", color = Color.White) },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError != null,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                errorBorderColor = Color.Red,
                cursorColor = Color.White
            )
        )
        if (passwordError != null) {
            Text(text = passwordError!!, color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (loginError != null) {
            Text(text = loginError!!, color = Color.Red, fontSize = 12.sp)
        }

        Button(
            onClick = {
                var isValid = true
                if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailError = "Please enter a valid email address"
                    isValid = false
                }
                if (password.isBlank()) {
                    passwordError = "Password cannot be empty"
                    isValid = false
                }

                if (isValid) {
                    val loginViewModel = LoginViewModel()
                    loginViewModel.loginUser(email, password) { success, error, user ->
                        if (success && user != null) {
                            val gson = Gson()
                            val userJson = gson.toJson(user)
                            val navigate = Intent(context, ScreenPrincipalViewModel::class.java).apply {
                                putExtra("user", userJson)
                            }
                            context.startActivity(navigate)
                        } else {
                            loginError = error
                        }
                    }
                }
            },
            modifier = Modifier
                .height(50.dp)
                .width(140.dp),
            shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp),
            enabled = true,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 30.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFC5F2D),
                contentColor = Color.Green,
                disabledContainerColor = Color.LightGray,
                disabledContentColor = Color.White
            )
        ) {
            Text(text = "Login", color = Color.White)
        }
    }
}