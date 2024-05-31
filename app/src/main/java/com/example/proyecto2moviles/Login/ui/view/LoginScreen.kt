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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto2moviles.Login.ui.viewModel.LoginViewModel
import com.example.proyecto2moviles.R
import com.example.proyecto2moviles.Screens.ui.viewModel.ScreenPrincipalViewModel
import com.google.firebase.database.DatabaseReference
import com.google.gson.Gson

@Composable
fun LoginScreen(context: Context, databaseReference: DatabaseReference) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var errors by remember {
        mutableStateOf(LoginErrors())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginImage()
        Spacer(modifier = Modifier.height(16.dp))
        LoginWelcomeText()
        Spacer(modifier = Modifier.height(8.dp))
        LoginSubtitleText()
        Spacer(modifier = Modifier.height(16.dp))
        EmailTextField(email, errors.emailError) {
            email = it
            errors = errors.copy(emailError = null, loginError = null)
        }
        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField(password, passwordVisible, errors.passwordError, onPasswordChange = {
            password = it
            errors = errors.copy(passwordError = null, loginError = null)
        }) {
            passwordVisible = !passwordVisible
        }
        Spacer(modifier = Modifier.height(16.dp))
        errors.loginError?.let {
            Text(text = it, color = Color.Red, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        LoginButton(email, password, errors, context, onErrorChange = {
            errors = it
        })
    }
}

@Composable
fun LoginImage() {
    Image(
        painter = painterResource(id = R.drawable.a),
        contentDescription = "Login image",
        modifier = Modifier.size(200.dp)
    )
}

@Composable
fun LoginWelcomeText() {
    Text(
        text = "Welcome",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
}

@Composable
fun LoginSubtitleText() {
    Text(
        text = "Login to your account",
        color = Color.White
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(email: String, emailError: String?, onEmailChange: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
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
    emailError?.let {
        Text(text = it, color = Color.Red, fontSize = 12.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    password: String,
    passwordVisible: Boolean,
    passwordError: String?,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityChange: () -> Unit
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(text = "Password", color = Color.White) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (passwordVisible) "Hide password" else "Show password"
            IconButton(onClick = onPasswordVisibilityChange) {
                Icon(imageVector = image, contentDescription = description, tint = Color.White)
            }
        },
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
    passwordError?.let {
        Text(text = it, color = Color.Red, fontSize = 12.sp)
    }
}

@Composable
fun LoginButton(
    email: String,
    password: String,
    errors: LoginErrors,
    context: Context,
    onErrorChange: (LoginErrors) -> Unit
) {
    Button(
        onClick = {
            var isValid = true
            var newErrors = LoginErrors()

            if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                newErrors = newErrors.copy(emailError = "Please enter a valid email address")
                isValid = false
            }
            if (password.isBlank()) {
                newErrors = newErrors.copy(passwordError = "Password cannot be empty")
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
                        onErrorChange(errors.copy(loginError = error))
                    }
                }
            } else {
                onErrorChange(newErrors)
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

data class LoginErrors(
    val emailError: String? = null,
    val passwordError: String? = null,
    val loginError: String? = null
)
