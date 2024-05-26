package com.example.proyecto2moviles.Screens.ui.view

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PRScreen(activity: Activity){
    var benchPress by remember { mutableStateOf(TextFieldValue("")) }
    var shoulderPress by remember { mutableStateOf(TextFieldValue("")) }
    var snatch by remember { mutableStateOf(TextFieldValue("")) }
    var clean by remember { mutableStateOf(TextFieldValue("")) }
    var deadlift by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Personal Record", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp), color = Color.White)

        ExerciseInputField("Bench Press", benchPress) { benchPress = it }
        ExerciseInputField("Shoulder Press", shoulderPress) { shoulderPress = it }
        ExerciseInputField("Snatch", snatch) { snatch = it }
        ExerciseInputField("Clean", clean) { clean = it }
        ExerciseInputField("Deadlift", deadlift) { deadlift = it }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseInputField(label: String, value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                // Filtrar para que solo se acepten números
                val filteredValue = newValue.text.filter { it.isDigit() }
                onValueChange(TextFieldValue(filteredValue))
            },
            label = { Text(label, color = Color.White)},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.White
            ),
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.width(8.dp))

        Button(onClick = {
            // Manejar la acción del botón individual aquí
        },
            modifier = Modifier
                .height(40.dp)
                .width(100.dp),
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
            Text("Save", color = Color.White)
        }
    }
}
