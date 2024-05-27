package com.example.proyecto2moviles.Screens.ui.view

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto2moviles.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalcDumbbellScreen(activity: Activity) {
    var inputText by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }

    val backgroundPainter: Painter = painterResource(id = R.drawable.gym3)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = backgroundPainter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )



        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "Calculate Dumbbell",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Enter the total weight in lbs you want to lift:",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = inputText,
                onValueChange = {
                    inputText = it.filter { it.isDigit() }
                }, // Solo se aceptan dígitos
                modifier = Modifier.padding(8.dp),
                textStyle = TextStyle(fontSize = 18.sp, color = Color.White),
                label = { Text("Weight in lbs", color = Color.White) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.White
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val pesoTotal = inputText.toFloatOrNull()
                    if (pesoTotal != null) {
                        resultText = distribuirPeso(pesoTotal)
                    } else {
                        resultText = "Please enter a valid number."
                    }
                },
                modifier = Modifier
                    .height(50.dp)
                    .width(140.dp),
                shape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp),
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
                Text("Calculated", color = Color.White)
            }

            Text(
                resultText,
                fontSize = 20.sp,
                color = if (resultText == "Exact weight distribution cannot be achieved with the available discs.") Color.Red else Color.White,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

fun distribuirPeso(totalPeso: Float): String {
    val pesoBarra = 45f
    val discos = listOf(45f, 35f, 25f, 15f, 10f, 5f, 2.5f)
    var pesoRestante = totalPeso - pesoBarra
    val distribucion = mutableMapOf("Bar" to 1)

    for (disco in discos) {
        if (pesoRestante <= 0) break
        val cantidad = (pesoRestante / (disco * 2)).toInt() * 2
        if (cantidad > 0) {
            distribucion["Discs of $disco lbs"] = cantidad
            pesoRestante -= cantidad * disco
        }
    }

    return if (pesoRestante > 0) {
        "Exact weight distribution cannot be achieved with the available discs."
    } else {
        distribucion.entries.joinToString("\n") { (key, value) ->
            if (key == "Bar") "$key: $pesoBarra lbs" else "$key: $value"
        }
    }
}
